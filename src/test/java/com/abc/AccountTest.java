package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class AccountTest {

    private static final double EPSILON = 1e-15;

    @Test(expected = IllegalArgumentException.class)
    public void deposit() {
        final int DEPOSIT_AMOUNT = 20;
        CheckingAccount account = new CheckingAccount();
        account.deposit(DEPOSIT_AMOUNT);
        assertEquals(
                "Test balance of account with a single deposit is equal to the deposit amount",
                DEPOSIT_AMOUNT,
                account.getBalance(),
                EPSILON);

        final double SECOND_DEPOSIT_AMOUNT = 1.99;
        account.deposit(SECOND_DEPOSIT_AMOUNT);
        assertEquals(
                "Test that multiple deposits can be made",
                DEPOSIT_AMOUNT + SECOND_DEPOSIT_AMOUNT,
                account.getBalance(),
                EPSILON);

        assertTrue(
                "Test that deposit appears in statement",
                account.getStatement().contains(Double.toString(DEPOSIT_AMOUNT)));

        account.deposit(-20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdraw() {
        final double INITIAL_DEPOSIT = 80.50, WITHDRAWAL_AMOUNT = 20;
        SavingsAccount account = new SavingsAccount();
        account.deposit(INITIAL_DEPOSIT);
        account.withdraw(WITHDRAWAL_AMOUNT);
        assertEquals(
                "Test withdrawal is subtracted from account",
                INITIAL_DEPOSIT - WITHDRAWAL_AMOUNT,
                account.getBalance(),
                EPSILON);

        assertTrue(
                "Test that withdrawal appears on statement",
                account.getStatement().contains(Double.toString(WITHDRAWAL_AMOUNT)));

        account.withdraw(-4.99);
    }

    @Test
    public void getBalance() {
        MaxiSavingsAccount account = new MaxiSavingsAccount();
        assertEquals("Test that balance is initially zero", 0, account.getBalance(), EPSILON);

        final double WITHDRAWAL_AMOUNT = 5.65;
        account.withdraw(WITHDRAWAL_AMOUNT);
        assertEquals(
                "Test that withdrawals are subtracted",
                -WITHDRAWAL_AMOUNT,
                account.getBalance(),
                EPSILON);
    }

    @Test
    public void getAccountType() {
        final String CHECKING_ACCOUNT = "Checking Account",
                SAVINGS_ACCOUNT = "Savings Account",
                MAXI_SAVINGS_ACCOUNT = "Maxi Savings Account";

        CheckingAccount checkingAccount = new CheckingAccount();
        assertEquals(
                "Test checking account type is correct",
                CHECKING_ACCOUNT,
                checkingAccount.getAccountType());

        SavingsAccount savingsAccount = new SavingsAccount();
        assertEquals(
                "Test savings account type is correct",
                SAVINGS_ACCOUNT,
                savingsAccount.getAccountType());

        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
        assertEquals(
                "Test maxi savings account type is correct",
                MAXI_SAVINGS_ACCOUNT,
                maxiSavingsAccount.getAccountType());
    }

    @Test
    public void getId() {
        SavingsAccount savingsAccountOne = new SavingsAccount();
        int savingAccountOneId = savingsAccountOne.getId();
        assertNotNull("Test that ids are assigned to accounts", savingAccountOneId);

        SavingsAccount savingsAccountTwo = new SavingsAccount();
        assertEquals(
                "Test that ids auto increment", savingAccountOneId + 1, savingsAccountTwo.getId());
        CheckingAccount checkingAccount = new CheckingAccount();
        assertEquals(
                "Test that ids increment consistently across different account types",
                savingAccountOneId + 2,
                checkingAccount.getId());
    }

    @Test
    public void getStatement() {
        SavingsAccount savingsAccount = new SavingsAccount();
        assertTrue(
                "Test that statement contains account type",
                savingsAccount.getStatement().contains(savingsAccount.getAccountType()));

        final double DEPOSIT_AMOUNT = 5.50;
        final String DEPOSIT_TEXT = "Deposit", WITHDRAWAL_TEXT = "Withdrawal";
        savingsAccount.deposit(DEPOSIT_AMOUNT);

        assertTrue(
                "Test that transaction type appears on statement",
                savingsAccount.getStatement().contains(DEPOSIT_TEXT));
        assertTrue(
                "Test that transaction amount appears on statement",
                savingsAccount.getStatement().contains(DEPOSIT_TEXT));
        assertFalse(
                "Test that withdrawal does not appear in statement if no withdrawals have been made",
                savingsAccount.getStatement().contains(WITHDRAWAL_TEXT));

        final double SECOND_DEPOSIT_AMOUNT = 0.50;
        savingsAccount.deposit(SECOND_DEPOSIT_AMOUNT);
        assertTrue(
                "Test that statement contains current balance",
                savingsAccount
                        .getStatement()
                        .contains(Double.toString(DEPOSIT_AMOUNT + SECOND_DEPOSIT_AMOUNT)));
    }

    @Test
    public void interestEarned() {
        final double CHECKING_INTEREST_RATE = 0.001;
        final double FIRST_TRANSACTION_AMOUNT = 5000,
                SECOND_TRANSACTION_AMOUNT = 1000.25,
                THIRD_TRANSACTION_AMOUNT = 250;
        final int DAYS_SINCE_FIRST_TRANSACTION = 13,
                DAYS_SINCE_SECOND_TRANSACTION = 13,
                DAYS_SINCE_THIRD_TRANSACTION = 6;
        final int DAYS_IN_YEAR = 365;
        final MathContext MATH_CONTEXT = MathContext.DECIMAL128;
        final int BIG_DECIMAL_SCALE = 10;

        CheckingAccount checkingAccount = new CheckingAccount();

        checkingAccount.deposit(FIRST_TRANSACTION_AMOUNT);
        checkingAccount
                .getTransactions()
                .get(0)
                .setTransactionDate(ZonedDateTime.now().minusDays(DAYS_SINCE_FIRST_TRANSACTION));

        final BigDecimal INTEREST_EARNED_AFTER_FIRST_DEPOSIT =
                BigDecimal.valueOf(FIRST_TRANSACTION_AMOUNT)
                        .multiply(
                                BigDecimal.valueOf(CHECKING_INTEREST_RATE)
                                        .divide(BigDecimal.valueOf(DAYS_IN_YEAR), MATH_CONTEXT)
                                        .add(BigDecimal.valueOf(1))
                                        .pow(DAYS_SINCE_FIRST_TRANSACTION, MATH_CONTEXT))
                        .subtract(BigDecimal.valueOf(FIRST_TRANSACTION_AMOUNT));

        assertEquals(
                "Test interest on a single deposit is correctly calculated",
                INTEREST_EARNED_AFTER_FIRST_DEPOSIT.setScale(
                        BIG_DECIMAL_SCALE, RoundingMode.HALF_UP),
                checkingAccount.interestEarned().setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));

        checkingAccount.deposit(SECOND_TRANSACTION_AMOUNT);
        checkingAccount
                .getTransactions()
                .get(1)
                .setTransactionDate(ZonedDateTime.now().minusDays(DAYS_SINCE_SECOND_TRANSACTION));

        final BigDecimal INTEREST_EARNED_AFTER_SECOND_DEPOSIT =
                INTEREST_EARNED_AFTER_FIRST_DEPOSIT.add(
                        BigDecimal.valueOf(SECOND_TRANSACTION_AMOUNT)
                                .multiply(
                                        BigDecimal.valueOf(CHECKING_INTEREST_RATE)
                                                .divide(
                                                        BigDecimal.valueOf(DAYS_IN_YEAR),
                                                        MATH_CONTEXT)
                                                .add(BigDecimal.valueOf(1))
                                                .pow(DAYS_SINCE_SECOND_TRANSACTION, MATH_CONTEXT))
                                .subtract(BigDecimal.valueOf(SECOND_TRANSACTION_AMOUNT)));

        assertEquals(
                "Test that interest on two same-day deposits is correctly calculated",
                INTEREST_EARNED_AFTER_SECOND_DEPOSIT.setScale(
                        BIG_DECIMAL_SCALE, RoundingMode.HALF_UP),
                checkingAccount.interestEarned().setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));

        checkingAccount.deposit(THIRD_TRANSACTION_AMOUNT);

        checkingAccount
                .getTransactions()
                .get(2)
                .setTransactionDate(ZonedDateTime.now().minusDays(DAYS_SINCE_THIRD_TRANSACTION));

        final BigDecimal BALANCE_DAY_OF_THIRD_DEPOSIT =
                BigDecimal.valueOf(FIRST_TRANSACTION_AMOUNT + SECOND_TRANSACTION_AMOUNT)
                        .multiply(
                                BigDecimal.valueOf(CHECKING_INTEREST_RATE)
                                        .divide(BigDecimal.valueOf(DAYS_IN_YEAR), MATH_CONTEXT)
                                        .add(BigDecimal.valueOf(1))
                                        .pow(
                                                DAYS_SINCE_FIRST_TRANSACTION
                                                        - DAYS_SINCE_THIRD_TRANSACTION,
                                                MATH_CONTEXT));
        final BigDecimal INTEREST_EARNED_ON_THIRD_DEPOSIT =
                BALANCE_DAY_OF_THIRD_DEPOSIT
                        .add(BigDecimal.valueOf(THIRD_TRANSACTION_AMOUNT))
                        .multiply(
                                BigDecimal.valueOf(CHECKING_INTEREST_RATE)
                                        .divide(BigDecimal.valueOf(DAYS_IN_YEAR), MATH_CONTEXT)
                                        .add(BigDecimal.valueOf(1))
                                        .pow(DAYS_SINCE_THIRD_TRANSACTION, MATH_CONTEXT))
                        .subtract(
                                BigDecimal.valueOf(
                                        FIRST_TRANSACTION_AMOUNT
                                                + SECOND_TRANSACTION_AMOUNT
                                                + THIRD_TRANSACTION_AMOUNT));

        assertEquals(
                "Test that additional deposits made on different days are correctly calculated",
                INTEREST_EARNED_ON_THIRD_DEPOSIT.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP),
                checkingAccount.interestEarned().setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP));
    }
}
