package com.abc;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 0.01;

    //deposit tests
    @Test
    public void depositPos() {
        Account a = new Account(Account.CHECKING);
        a.deposit(100);
        assertEquals(100, a.sumTransactions(), DOUBLE_DELTA);
    }

    @Test (expected = IllegalArgumentException.class)
    public void depositNeg() {
        Account a = new Account(Account.CHECKING);
        a.deposit(-100);
    }

    //withdrawal tests
    @Test
    public void withdrawPos() {
        Account a = new Account(Account.CHECKING);
        a.deposit(100);
        a.withdraw(50);
        assertEquals(50, a.sumTransactions(), DOUBLE_DELTA);
    }

    @Test (expected = IllegalArgumentException.class)
    public void withdrawNeg() {
        Account a = new Account(Account.CHECKING);
        a.withdraw(-100);
    }

    @Test (expected = IllegalStateException.class)
    public void overWithdraw() {
        Account a = new Account(Account.CHECKING);
        a.withdraw(100);
    }

    //interestEarned tests
    @Test
    public void checkingAccount() throws NoSuchFieldException, IllegalAccessException {
        Account account = new Account(Account.CHECKING);
        Transaction t = account.deposit(100.0);
        final Field f = Transaction.class.getDeclaredField("transactionDate");
        f.setAccessible(true);
        f.set(t, DateProvider.getInstance().daysAgo(365));
        assertEquals(0.1000, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() throws NoSuchFieldException, IllegalAccessException {
        Account account = new Account(Account.SAVINGS);
        Transaction t = account.deposit(1500.0);
        final Field f = Transaction.class.getDeclaredField("transactionDate");
        f.setAccessible(true);
        f.set(t, DateProvider.getInstance().daysAgo(365));
        assertEquals(1.0+1.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountBefore10Days() throws NoSuchFieldException, IllegalAccessException {
        Account account = new Account(Account.MAXI_SAVINGS);
        final Field f = Transaction.class.getDeclaredField("transactionDate");
        f.setAccessible(true);
        Transaction t = account.deposit(3000.0);
        f.set(t, DateProvider.getInstance().daysAgo(366));
        t = account.withdraw(100.0);
        f.set(t, DateProvider.getInstance().daysAgo(1));
        assertEquals(153.8 + 0.01, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountAfter10Days() throws NoSuchFieldException, IllegalAccessException {
        Account account = new Account(Account.MAXI_SAVINGS);
        final Field f = Transaction.class.getDeclaredField("transactionDate");
        f.setAccessible(true);
        Transaction t = account.deposit(3000.0);
        f.set(t, DateProvider.getInstance().daysAgo(376));
        t = account.withdraw(100.0);
        f.set(t, DateProvider.getInstance().daysAgo(11));
        assertEquals(153.8 + 0.08 + 0.42, account.interestEarned(), DOUBLE_DELTA);
    }


    //statementForAccount test
    @Test
    public void statementForAccount() throws NoSuchFieldException, IllegalAccessException {
        Account account = new Account(Account.MAXI_SAVINGS);
        final Field f = Transaction.class.getDeclaredField("transactionDate");
        f.setAccessible(true);
        Transaction t = account.deposit(3000.0);
        f.set(t, DateProvider.getInstance().daysAgo(376));
        t = account.withdraw(100.0);
        f.set(t, DateProvider.getInstance().daysAgo(11));
        String expected = "Maxi Savings Account\n" +
                "- 01/03/2018 Deposit $3,000.00\n" +
                "- 01/03/2019 Withdrawal $100.00\n" +
                "Total $2,900.00";
        assertEquals(expected, account.statementForAccount());
    }
}
