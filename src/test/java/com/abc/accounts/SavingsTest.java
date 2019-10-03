package com.abc.accounts;

import com.abc.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class SavingsTest {
    private Savings testAccount;
    private final double DOUBLE_DELTA = 1e-15;
    private final long DAY_IN_MS = 1000 * 60 * 60 * 24;

    @Before
    public void init() {
        testAccount = new Savings();

        // Set initial deposit to 1 year ago
        Date now = Calendar.getInstance().getTime();
        Date initDate = new Date(now.getTime() - 365*DAY_IN_MS);
        Transaction initTransaction = new Transaction(1000, initDate);
        testAccount.addTransaction(initTransaction);
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

    @Test // Check basic interest calculation
    public void testCalculateInterest() {
        double expectedInterest = 1;
        double actualInterest = testAccount.calculateInterest(365, 1000);
        assertEquals(expectedInterest, actualInterest, DOUBLE_DELTA);
    }

    @Test // Check total interest earned, 1 year on $1000
    public void testInterestEarnedSimple() {
        testAccount.withdraw(100);
        double expectedInterest = 1.0;
        double actualInterest = testAccount.interestEarned();
        assertEquals(expectedInterest, actualInterest, DOUBLE_DELTA);
    }

    @Test // Check total interest earned, with multiple transactions at different times
    public void testInterestEarnedComplex() {
        Date now = Calendar.getInstance().getTime();
        Date d1 = new Date(now.getTime() - 335*DAY_IN_MS);
        Date d2 = new Date(now.getTime() - 35*DAY_IN_MS);

        Transaction t1 = new Transaction(500, d1);
        Transaction t2 = new Transaction(-250, d2);
        testAccount.addTransaction(t1);
        testAccount.addTransaction(t2);

        double expectedInterest = 1.870329168339687;
        double actualInterest = testAccount.interestEarned();
        assertEquals(expectedInterest, actualInterest, DOUBLE_DELTA);
    }
}
