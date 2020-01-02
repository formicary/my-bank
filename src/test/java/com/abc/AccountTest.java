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

    @Test
    public void interestOnNewCheckingAccountIs0() {
        Account checkingAccount = new Account(Account.CHECKING);
        assertEquals(0, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestOnCheckingAccountWith1000Is1() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1000);
        assertEquals(1, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestOnNewSavingsAccountIs0() {
        Account checkingAccount = new Account(Account.SAVINGS);
        assertEquals(0, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestOnSavingsAccountWith1000Is1() {
        Account checkingAccount = new Account(Account.SAVINGS);
        checkingAccount.deposit(1000);
        assertEquals(1, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestOnSavingsAccountWith1500Is2() {
        Account checkingAccount = new Account(Account.SAVINGS);
        checkingAccount.deposit(1500);
        assertEquals(2, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestOnNewMaxiSavingsAccountIs0() {
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        assertEquals(0, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestOnMaxiSavingsAccountWith1000Is20() {
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        checkingAccount.deposit(1000);
        assertEquals(20, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestOnMaxiSavingsAccountWith2000Is70() {
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        checkingAccount.deposit(2000);
        assertEquals(70, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestOnMaxiSavingsAccountWith2010Is71() {
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        checkingAccount.deposit(2010);
        assertEquals(71, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }
}
