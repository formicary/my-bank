package com.abc;

import java.time.LocalDate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private static final double PENNY_DELTA = 0.001;

    @Test
    public void testDeposit() {
        Account account = new CheckingAccount();

        account.deposit(1000.0, LocalDate.now().minusDays(10));

        Transaction tx0 = account.transactions.get(0);
        assertEquals(1000.0, tx0.amount, DOUBLE_DELTA);
        assertEquals(LocalDate.now().minusDays(10), tx0.date);

        account.deposit(500.0);

        Transaction tx1 = account.transactions.get(1);
        assertEquals(500.0, tx1.amount, DOUBLE_DELTA);
        assertEquals(LocalDate.now(), tx1.date);
    }

    @Test
    public void testWithdraw() {
        Account account = new CheckingAccount();

        account.withdraw(1000.0, LocalDate.now().minusDays(10));

        Transaction tx0 = account.transactions.get(0);
        assertEquals(-1000.0, tx0.amount, DOUBLE_DELTA);
        assertEquals(LocalDate.now().minusDays(10), tx0.date);

        account.withdraw(500.0);

        Transaction tx1 = account.transactions.get(1);
        assertEquals(-500.0, tx1.amount, DOUBLE_DELTA);
        assertEquals(LocalDate.now(), tx1.date);
    }

    @Test
    public void testSumTransactions() {
        Account account = new CheckingAccount();

        account.deposit(1000.0, LocalDate.now().minusDays(10));
        assertEquals(1000.0, account.sumTransactions(), DOUBLE_DELTA);

        account.deposit(1000.0);
        assertEquals(2000.0, account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void testCurrentBalance() {
        Account account = new CheckingAccount();

        account.deposit(1000.0);
        assertEquals(1000.0, account.currentBalance(), DOUBLE_DELTA);

        account.deposit(1000.0);
        assertEquals(2000.0, account.currentBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testCheckingInterest() {
        Account account = new CheckingAccount();

        account.deposit(1000.0, LocalDate.now().minusDays(30));
        assertEquals(0.082, account.interestEarned(), PENNY_DELTA);

        account.deposit(1000.0, LocalDate.now().minusDays(15));
        assertEquals(0.123, account.interestEarned(), PENNY_DELTA);
    }

    @Test
    public void testSavingsInterest() {
        Account account = new SavingsAccount();

        account.deposit(1000.0, LocalDate.now().minusDays(30));
        assertEquals(0.082, account.interestEarned(), PENNY_DELTA);

        account.deposit(1000.0, LocalDate.now().minusDays(15));
        assertEquals(0.164, account.interestEarned(), PENNY_DELTA);
    }

    @Test
    public void testMaxiSavingsInterest() {
        Account account = new MaxiSavingsAccount();

        account.deposit(1000.0, LocalDate.now().minusDays(30));
        assertEquals(1.647, account.interestEarned(), PENNY_DELTA);

        account.deposit(1000.0, LocalDate.now().minusDays(20));
        assertEquals(4.397, account.interestEarned(), PENNY_DELTA);

        account.deposit(1000.0, LocalDate.now().minusDays(10));
        assertEquals(7.140, account.interestEarned(), PENNY_DELTA);
    }

}
