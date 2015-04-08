package com.abc;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void test_generation(){
        Account account = new Account(Account.Type.SAVINGS);
        assertTrue(account instanceof Account);
    }

    @Test
    public void test_deposits(){
        Account account = new Account(Account.Type.SAVINGS);
        account.deposit(1000);
        account.deposit(1000);
        account.deposit(1000);
        assertEquals(3000, account.sumTransactions(), DOUBLE_DELTA);
    }
    
    @Test
    public void test_withdrawals(){
        Account account = new Account(Account.Type.SAVINGS);
        account.deposit(10000);
        account.withdraw(2000);
        account.withdraw(2000);
        account.withdraw(2000);
        account.withdraw(2000);
        assertEquals(2000, account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    /*
        Depreciated constructor, but calculating time with milliseconds is a pain.
     */
    public void test_ten_days(){
        Account account = new Account(Account.Type.SAVINGS);
        Date first = new Date(10,10,15);
        Date second = new Date(10,10,10);
        assertTrue(account.withinTenDays(first, second));
    }

    @Test
    public void test_ten_days_2(){
        Account account = new Account(Account.Type.SAVINGS);
        Date first = new Date(10,10,15);
        Date second = new Date(10,10,5);
        assertTrue(account.withinTenDays(first, second));
    }

    @Test
    public void test_ten_days_3(){
        Account account = new Account(Account.Type.SAVINGS);
        Date first = new Date(10,10,15);
        Date second = new Date(10,10,1);
        assertFalse(account.withinTenDays(first, second));
    }
}
