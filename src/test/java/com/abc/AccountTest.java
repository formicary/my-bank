package com.abc;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tezivanovic on 11/05/2017.
 */
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private static final double DOUBLE_DAILY_INTEREST = 0.001/365;

    @Test
    public void testDeposit() {
        Account account = new Account(Account.SAVINGS);
        account.deposit(500);
        assertEquals(500, account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdraw() {
        Account account = new Account(Account.SAVINGS);
        account.withdraw(500);
        assertEquals(-500, account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarned() {

        Account account = new Account(Account.SAVINGS);
        account.deposit(500);
        assertEquals(500*DOUBLE_DAILY_INTEREST, account.interestEarned(), DOUBLE_DELTA);
        account.deposit(2000);
        assertEquals(4000*DOUBLE_DAILY_INTEREST, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testSumTransactions() {

        Account account = new Account(Account.SAVINGS);
        account.deposit(500);
        account.withdraw(200);
        account.withdraw(100);
        account.deposit(350);
        assertEquals(550, account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void testGetAccountType() {
        Account account = new Account(Account.CHECKING);
        assertEquals(0, account.getAccountType());
    }

    @Test
    public void testAccountMaxi(){
        Account account = new Account(Account.MAXI_SAVINGS);
        account.deposit(10000);
        assertEquals(10000*0.05/365, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testAccountMaxi2(){
        Account account = new Account(Account.MAXI_SAVINGS);
        account.deposit(10000);
        account.withdraw(2000);
        assertEquals(8000*DOUBLE_DAILY_INTEREST, account.interestEarned(), DOUBLE_DELTA);
    }

}