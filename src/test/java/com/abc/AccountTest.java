package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/* Written by Tunc Demircan */
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test(expected = IllegalArgumentException.class)
    public void testAccountTypeConstraintP(){
        Account illegalAcc = new Account(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAccountTypeConstraintN(){
        Account illegalAcc = new Account(-1);
    }

    @Test
    public void testAccountTypeName(){
        Account checking = new Account(0);
        Account savings = new Account(1);
        Account maxi = new Account(2);

        assertEquals("Checking Account", checking.accountTypeName());
        assertEquals("Savings Account", savings.accountTypeName());
        assertEquals("Maxi-Savings Account", maxi.accountTypeName());
    }

    @Test
    public void testDeposit(){
        Account acc = new Account(0);

        assertEquals(0.0, acc.getBalance(), DOUBLE_DELTA);
        acc.deposit(10.125);
        assertEquals(10.125, acc.getBalance(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawFail(){
        Account acc = new Account(0);

        acc.withdraw(1);
    }

    @Test
    public void testWithdraw(){
        Account acc = new Account(0);
        acc.deposit(10.125);

        acc.withdraw(1);
        assertEquals(9.125, acc.getBalance(), DOUBLE_DELTA);

        acc.withdraw(9.125);
        assertEquals(0, acc.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testChecking(){
        Account checking = new Account(0);
        checking.deposit(10.0);
        assertEquals(0.01, checking.interest(), DOUBLE_DELTA);
    }

    @Test
    public void testSavings(){
        Account savings = new Account(1);
        savings.deposit(10.0);
        assertEquals(0.01, savings.interest(), DOUBLE_DELTA);

        savings.deposit(991.0);
        assertEquals(1.002, savings.interest(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxi(){
        Account maxi = new Account(2);
        maxi.deposit(10.0);
        assertEquals(0.5, maxi.interest(), DOUBLE_DELTA);

        maxi.withdraw(1.0);
        assertEquals(0.009, maxi.interest(), DOUBLE_DELTA);
    }

    @Test
    public void testPayInterest(){
        Account acc = new Account(0);
        acc.deposit(10.0);

        acc.payInterest(2);
        assertEquals(10.02001, acc.getBalance(), DOUBLE_DELTA);
        assertEquals(0.02001, acc.getInterestPaid(), DOUBLE_DELTA);

    }
}
