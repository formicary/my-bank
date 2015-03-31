package com.abc;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author R. Fei
 */
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    public AccountTest() {
    }
    /**
     * Test of deposit, withdraw, getBalance,
     * of class Account and its sub-classes.
     */
    @Test
    public void testdepositwithdraw() {
        System.out.println("Deposit + Withdraw");

        Account instance[] = { new Account(), new  AccountChecking(),
                               new AccountSavings(), new AccountMaxiSavings()};
        
        instance[0].deposit(1500);  instance[0].withdraw(500);
        instance[1].deposit(1500);  instance[1].withdraw(500);
        instance[2].deposit(1500);  instance[2].withdraw(500);
        instance[3].deposit(1500);  instance[3].withdraw(500);
        
        double[] expResult = {1000, 1000, 1000, 1000};
        double[] result = {instance[0].getBalance(),
                           instance[1].getBalance(),
                           instance[2].getBalance(),
                           instance[3].getBalance()};
        assertArrayEquals(expResult, result, DOUBLE_DELTA);  
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test of TransferMoney of class Account.
     */
    @Test
    public void testTransferMoney() {
        System.out.println("Transfer Money");
        
        Account instance = new AccountChecking();
        Account Destination = new AccountChecking();
        instance.deposit(1500);
        
        double amount = 500;
        instance.TransferMoney(amount, Destination);
        double[] expResult = {1500 - amount, amount};
        double[] result = {instance.getBalance(), Destination.getBalance()};

        assertArrayEquals(expResult, result, DOUBLE_DELTA);  
        //TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }   
    
    /**
     * Test of deposit(with Date), withdraw(with Date) of class Account
     * Test of interestEarned method of Checking Account.
     */
    @Test
    public void testInterestEarnedCheckings() {
        System.out.println("Transaction (withDate) + Interest Earned (Checking Account)");

        testAccount instance = new AccountChecking();
        Date inDate1 = new Date(115,0,1); //"2015-01-01", "yyyy-MM-dd"
        Date outDate2 = new Date(115,1,1); //"2015-02-01", "yyyy-MM-dd"
        
        instance.depositwithDate(1500, inDate1);
        instance.withdrawwithDate(500, outDate2);        
        
        Date Now = new Date();
        int days = (int)((Now.getTime() - outDate2.getTime())
                                                        /(24 * 60 * 60 * 1000));
        
        double expResult = 1500.0*31 * 0.001/365 + 1000.0*days * 0.001/365;
        double result = instance.interestEarned();
        assertEquals(expResult, result, DOUBLE_DELTA);
        //TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test of interestEarned method, of Savings Account.
     */
    @Test
    public void testInterestEarnedSavings() {
        System.out.println("Interest Earned (Savings Account)");
        
        testAccount instance = new AccountSavings();
        Date inDate1 = new Date(115,0,1); //"2015-01-01", "yyyy-MM-dd"
        Date outDate2 = new Date(115,1,1); //"2015-02-01", "yyyy-MM-dd"
        
        instance.depositwithDate(1500, inDate1);        
        instance.withdrawwithDate(800, outDate2);
        
        double expResult1 = 1500;
        double result1 = instance.getBalancesList().get(0).amount;
        assertEquals(expResult1, result1, DOUBLE_DELTA);
        
        double expResult2 = 700;
        double result2 = instance.getBalancesList().get(1).amount;
        assertEquals(expResult2, result2, DOUBLE_DELTA);
        
        Date Now = new Date();
        int days = (int)((Now.getTime() - outDate2.getTime())
                                                        /(24 * 60 * 60 * 1000));
        
        double expResult = (1+500.0*0.002)*31/365 + 700*days*0.001/365;
        double result = instance.interestEarned();
        assertEquals(expResult, result, DOUBLE_DELTA);
        //TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test of interestEarned method, of Maxi Savings Account.
     */
    @Test
    public void testInterestEarnedMaxiSavings() {
        System.out.println("Interest Earned (Maxi Savings Account)");

        testAccount instance = new AccountMaxiSavings();
        Date Date1 = new Date(115,0,1); //"2015-01-01", "yyyy-MM-dd"
        Date Date2 = new Date(115,0,20); //"2015-01-20", "yyyy-MM-dd"
        Date Date3 = new Date(115,0,25); //"2015-01-25", "yyyy-MM-dd"
        Date Date4 = new Date(115,1,1); //"2015-02-01", "yyyy-MM-dd"
        
        instance.depositwithDate(1500, Date1);
        instance.depositwithDate(500, Date2);
        instance.withdrawwithDate(500, Date3);
        instance.depositwithDate(500, Date4);
        
        Date Now = new Date();
        int days = (int)((Now.getTime() - Date4.getTime())
                                                        /(24 * 60 * 60 * 1000));
        
        double expResult = (1500*10*0.001+1500*9*0.05+
                2000*5*0.05+
                1500*0.001*7+
                2000*3*0.001+2000*(days-3)*0.05) / 365;
        double result = instance.interestEarned();
        assertEquals(expResult, result, DOUBLE_DELTA);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test(expected = Exception.class)
    public void testExceptions00(){
        System.out.println("Invalid Amount-1");
        Account instance = new Account();
        expect(IllegalArgumentException.class);
        instance.withdraw(-30); //invalid amount
    }
    @Test(expected = Exception.class)
    public void testExceptions01(){
        System.out.println("Invalid Amount-2");
        Account instance = new Account();
        expect(IllegalArgumentException.class);
        instance.deposit(-30); //invalid amount
    }
    @Test(expected = Exception.class)
    public void testExceptions02(){
        System.out.println("Invalid Amount-3");        
        Account instance = new Account();
        Account destination = new Account();
        instance.TransferMoney(-100, destination); //overdraft
        expect(IllegalArgumentException.class);
    }
    
    @Test(expected = Exception.class)
    public void testExceptions10(){
        System.out.println("Overdraft-1");
        Account instance = new Account();
        expect(IllegalArgumentException.class);
        instance.withdraw(30); //overdraft
    }
    @Test(expected = Exception.class)
    public void testExceptions11(){
        System.out.println("Overdraft-2");        
        Account instance = new Account();
        Account destination = new Account();
        expect(IllegalArgumentException.class);
        instance.TransferMoney(100, destination); //overdraft
    }
    
    private void expect(Class<IllegalArgumentException> aClass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
