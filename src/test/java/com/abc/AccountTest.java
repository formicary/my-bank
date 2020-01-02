package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test(expected = IllegalArgumentException.class)
    public void zeroDepositThrowsException() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(0);
    }

    @Test
    public void positiveDepositAmount() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1);
        assertEquals(1, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeDepositThrowsException() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroWithdrawThrowsException() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.withdraw(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeWithdrawThrowsException() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.withdraw(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void positiveWithdrawWithInsufficientBalance() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.withdraw(1);
    }

    @Test
    public void positiveWithdrawWithSufficientBalance() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1);
        checkingAccount.withdraw(1);
        assertEquals(0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
}
