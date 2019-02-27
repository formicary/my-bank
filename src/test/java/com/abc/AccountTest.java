package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCheckingAccount() {
        Account account = new CheckingAccount();

        account.deposit(100.0);
        assertEquals(0.1, account.interestEarned(), DOUBLE_DELTA);

        account.deposit(100.0);
        assertEquals(0.2, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccount() {
        Account account = new SavingsAccount();

        account.deposit(500.0);
        assertEquals(0.5, account.interestEarned(), DOUBLE_DELTA);

        account.deposit(500.0);
        assertEquals(1.0, account.interestEarned(), DOUBLE_DELTA);

        account.deposit(500.0);
        assertEquals(2.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccount() {
        Account account = new MaxiSavingsAccount();

        account.deposit(500.0);
        assertEquals(10.0, account.interestEarned(), DOUBLE_DELTA);

        account.deposit(500.0);
        assertEquals(20.0, account.interestEarned(), DOUBLE_DELTA);

        account.deposit(500.0);
        assertEquals(45.0, account.interestEarned(), DOUBLE_DELTA);

        account.deposit(500.0);
        assertEquals(70.0, account.interestEarned(), DOUBLE_DELTA);

        account.deposit(500.0);
        assertEquals(120.0, account.interestEarned(), DOUBLE_DELTA);
    }

}
