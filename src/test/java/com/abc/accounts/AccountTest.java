package com.abc.accounts;

import com.abc.Transaction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class AccountTest {
    private Account testAccount;
    private final double DOUBLE_DELTA = 1e-15;

    @Before
    public void init() {
        testAccount = new Account() {
            @Override
            public double interestEarned() {
                return 0;
            }
        };
        testAccount.deposit(1000);
    }

    @Test // Check balance after depositing
    public void testDeposit() {
        testAccount.deposit(50);
        double expected = 1050;
        double actual = testAccount.getBalance();
        assertEquals(expected, actual, DOUBLE_DELTA);
    }

    @Test // Check balance after withdrawing
    public void testWithdraw() {
        testAccount.withdraw(50);
        double expected = 950;
        double actual = testAccount.getBalance();
        assertEquals(expected, actual, DOUBLE_DELTA);
    }

    @Test // Check multiple deposits
    public void testMultipleDeposits() {
        testAccount.deposit(50);
        testAccount.deposit(35);
        testAccount.deposit(0.01);
        double expected = 1085.01;
        double actual = testAccount.getBalance();

        // Check balance
        assertEquals(expected, actual, DOUBLE_DELTA);
        // Check number of transactions
        assertEquals(4, testAccount.getTransactions().size());
    }

    @Test // Check multiple withdraws
    public void testMultipleWithdraws() {
        testAccount.withdraw(50);
        testAccount.withdraw(35);
        testAccount.withdraw(0.01);
        double expected = 914.99;
        double actual = testAccount.getBalance();

        // Check balance
        assertEquals(expected, actual, DOUBLE_DELTA);
        // Check number of transactions
        assertEquals(4, testAccount.getTransactions().size());
    }
}
