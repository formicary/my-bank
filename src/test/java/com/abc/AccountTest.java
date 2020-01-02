package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test(expected = IllegalArgumentException.class)
    public void depositZeroThrowsException() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(0);
    }

    @Test
    public void depositOneHasBalanceOne() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1);
        assertEquals(1, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositNegativeOneThrowsException() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawZeroThrowsException() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.withdraw(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegativeOneThrowsException() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.withdraw(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawOneWithZeroBalanceThrowsException() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.withdraw(1);
    }

    @Test
    public void withdrawOneWithOneBalanceThrowsException() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1);
        checkingAccount.withdraw(1);
        assertEquals(0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
}
