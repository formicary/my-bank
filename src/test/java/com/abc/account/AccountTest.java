package com.abc.account;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    private static final double DELTA = 1e-15;

    @Test
    public void When_DepositPositiveAmount_Expect_DepositToSucceed() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(20.5);

        assertEquals(1, account.getTransactions().size());
        assertEquals(20.5, account.getTransactions().get(0).getAmount(), DELTA);
        assertNotNull(account.getTransactions().get(0).getTransactionDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void When_DepositNegativeAmount_Expect_ExceptionIsThrown() {
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(-5.0);
    }

    @Test
    public void When_EnoughMoneyToWithdraw_Expect_WithdrawToSucceed() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(100);
        account.withdraw(20.5);

        assertEquals(2, account.getTransactions().size());
        assertEquals(-20.5, account.getTransactions().get(1).getAmount(), DELTA);
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
        account.deposit(1000.0);
        account.withdraw(200.0);
        account.deposit(50.0);

        assertEquals(850.0, account.sumOfTransactions(), DELTA);
    }

    @Test
    public void When_AccountHasMultipleTransactions_Expect_StatementToBeCorrect() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(500.0);
        account.withdraw(30.0);

        assertEquals("Checking Account\n" +
                "  deposit $500.00\n" +
                "  withdrawal $30.00\n" +
                "Total $470.00", account.statementForAccount());
    }
}
