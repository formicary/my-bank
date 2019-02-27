package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCheckingAccount() {
        Account checkingAccount = new Account(Account.CHECKING);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccount() {
        Account savingsAccount = new Account(Account.SAVINGS);

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccount() {
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
