package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

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
    //statementForAccount
}
