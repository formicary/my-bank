package com.abc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void deposit() {
        Account account = new Account(Account.Type.CHECKING);
        account.deposit(300.15);
        double expected = 300.15;

        Assert.assertEquals(expected, account.sumTransactions(), 0);
    }

    @Test
    public void withdraw() {
        Account account = new Account(Account.Type.CHECKING);
        account.deposit(300);
        account.withdraw(100);

        Assert.assertEquals(200, account.sumTransactions(), 0);
    }

    @Test
    public void highWithdraw() {
        Account account = new Account(Account.Type.CHECKING);
        account.deposit(300);

        boolean exceptionCaught = false;
        try {
            account.withdraw(1500);
        } catch (IllegalArgumentException ex) {
        Assert.assertEquals("Not enough money on your account.", ex.getMessage());
        exceptionCaught = true;
    }
        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void interestEarned() {

        Account checkingAccount = new Account(Account.Type.CHECKING);
        checkingAccount.deposit(100.0);

        assertEquals(0.1, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void sumTransactions() {
        Account checkingAccount = new Account(Account.Type.CHECKING);
        checkingAccount.deposit(100.0);
        checkingAccount.deposit(500.0);
        checkingAccount.deposit(600.0);

        assertEquals(1200, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
}
