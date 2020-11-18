package com.abc.account;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    private static final double DELTA = 1e-15;

    @Test
    public void When_DepositPositiveAmount_Expect_DepositToSucceed() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(20.5, TransactionType.CUSTOMER_DEPOSIT);

        assertEquals(1, account.getTransactions().size());
        assertEquals(20.5, account.getTransactions().get(0).getAmount(), DELTA);
        assertEquals(TransactionType.CUSTOMER_DEPOSIT, account.getTransactions().get(0).getType());
        assertNotNull(account.getTransactions().get(0).getTransactionDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void When_DepositNegativeAmount_Expect_ExceptionIsThrown() {
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(-5.0, TransactionType.CUSTOMER_DEPOSIT);
    }

    @Test
    public void When_EnoughMoneyToWithdraw_Expect_WithdrawToSucceed() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(100, TransactionType.CUSTOMER_DEPOSIT);
        account.withdraw(20.5);

        assertEquals(2, account.getTransactions().size());
        assertEquals(-20.5, account.getTransactions().get(1).getAmount(), DELTA);
        assertEquals(TransactionType.CUSTOMER_WITHDRAWAL, account.getTransactions().get(1).getType());
        assertNotNull(account.getTransactions().get(1).getTransactionDate());
        assertEquals(79.5, account.sumOfTransactions(), DELTA);
    }

    @Test(expected = IllegalStateException.class)
    public void When_NotEnoughMoneyToWithdraw_Expect_ExceptionIsThrown() {
        Account account = new Account(AccountType.SAVINGS);
        account.withdraw(100.0);
    }

    @Test
    public void When_AccountHasMultipleTransactions_Expect_SumOfTransactionsToBeCorrect() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(1000.0, TransactionType.CUSTOMER_DEPOSIT);
        account.withdraw(200.0);
        account.deposit(50.0, TransactionType.CUSTOMER_DEPOSIT);
        account.addDailyInterestToAccount();

        assertEquals(850.0023287671232, account.sumOfTransactions(), DELTA);
        assertEquals(4, account.getTransactions().size());
    }

    @Test
    public void When_AccountHasMultipleTransactions_Expect_StatementToBeCorrect() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(500.0, TransactionType.CUSTOMER_DEPOSIT);
        account.withdraw(30.0);
        account.addDailyInterestToAccount();

        assertEquals("Checking Account\n" +
                "  deposit $500.00\n" +
                "  withdrawal $30.00\n" +
                "  deposit $0.00\n" +
                "Total $470.00", account.statementForAccount());
    }

    @Test
    public void When_DailyInterestIsAddedToAccount_Expect_AccountTotalToBeCorrect() {
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(1000.0, TransactionType.CUSTOMER_DEPOSIT);

        account.addDailyInterestToAccount();

        assertEquals(2, account.getTransactions().size());
        assertEquals(TransactionType.INTEREST_ADDED, account.getTransactions().get(1).getType());
        assertEquals(1000.0027397260274, account.sumOfTransactions(), DELTA);
    }

    @Test
    public void When_MultipleInterestsWereAddedToAccount_Expect_TotalInterestEarnedToBeCorrect() {
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(2000.0, TransactionType.CUSTOMER_DEPOSIT);
        account.addDailyInterestToAccount();
        account.deposit(500.0, TransactionType.CUSTOMER_DEPOSIT);
        account.addDailyInterestToAccount();

        assertEquals(0.6164758866579096, account.interestEarned(), DELTA);
    }
}
