package com.abc;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    //deposit
    @Test
    public void depositPos()
    {
        Account a = new Account(Account.CHECKING);
        a.deposit(100);
        assertEquals(100, a.sumTransactions(), DOUBLE_DELTA);
    }

    @Test (expected = IllegalArgumentException.class)
    public void depositNeg()
    {
        Account a = new Account(Account.CHECKING);
        a.deposit(-100);
    }

    //withdrawal
    @Test
    public void withdrawPos()
    {
        Account a = new Account(Account.CHECKING);
        a.deposit(100);
        a.withdraw(50);
        assertEquals(50, a.sumTransactions(), DOUBLE_DELTA);
    }

    @Test (expected = IllegalArgumentException.class)
    public void withdrawNeg()
    {
        Account a = new Account(Account.CHECKING);
        a.withdraw(-100);
    }

    @Test (expected = IllegalStateException.class)
    public void overWithdraw()
    {
        Account a = new Account(Account.CHECKING);
        a.withdraw(100);
    }

    //interestEarned
    @Test
    public void checkingAccount() {
        Account account = new Account(Account.CHECKING);
        account.deposit(100.0);

        assertEquals(0.1, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Account account = new Account(Account.SAVINGS);
        account.deposit(1500.0);

        assertEquals(2.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountBefore10Days() throws NoSuchFieldException, IllegalAccessException {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.deposit(3000.0);
        Transaction t = account.withdraw(100.0);
        final Field f = Transaction.class.getDeclaredField("transactionDate");
        f.setAccessible(true);
        f.set(t, DateProvider.getInstance().daysAgo(15));
        assertEquals(145.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountAfter10Days(){
        Account account = new Account(Account.MAXI_SAVINGS);
        account.deposit(3000.0);
        account.withdraw(100.0);
        assertEquals(2.9, account.interestEarned(), DOUBLE_DELTA);
    }


    //statementForAccount
}
