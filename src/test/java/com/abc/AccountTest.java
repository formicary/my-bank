/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;

/**
 *Requirements tested:
 * Checking accounts have a flat rate of 0.1%
 * Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%
 * Change Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
 * 
 * @author Andreas Neokleous
 */
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    /**
     * Checking account interest
     */
    @Test
    public void checkingAccount(){
         Customer john = new Customer("John").openAccount(new Account(Account.CHECKING));
         john.getAccount(Account.CHECKING).deposit(1000.0);
         assertEquals(1.0, john.totalInterestEarned(), DOUBLE_DELTA);       
         
         john.getAccount(Account.CHECKING).deposit(2000.0);
         assertEquals(3.0, john.totalInterestEarned(), DOUBLE_DELTA);       
    }
    
    /**
     * Savings account interest
     */
    @Test
    public void savingsAccount(){
         Customer john = new Customer("John").openAccount(new Account(Account.SAVINGS));
         john.getAccount(Account.SAVINGS).deposit(1000.0);
         assertEquals(1.0, john.totalInterestEarned(), DOUBLE_DELTA);       
         
         john.getAccount(Account.SAVINGS).deposit(2000.0);
         assertEquals(5.0, john.totalInterestEarned(), DOUBLE_DELTA);       
    }
    
    /**
     * Maxi Savings interest
     * Last transaction is now, therefore 0.1% interest rate.
     */  
    @Test
    public void maxiSavingsAccount(){
         Customer john = new Customer("John").openAccount(new Account(Account.MAXI_SAVINGS));
         john.getAccount(Account.MAXI_SAVINGS).deposit(1000.0);
         assertEquals(1.0, john.totalInterestEarned(), DOUBLE_DELTA);       
         
         john.getAccount(Account.MAXI_SAVINGS).deposit(2000.0);
         assertEquals(3.0, john.totalInterestEarned(), DOUBLE_DELTA);       
    }
    
    /**
     * Maxi Savings interest - 10day old
     * Test if last transaction in Maxi Savings is 10 days old, therefore 5% interest rate.
     */
    @Test
    public void maxiSavingsAccount10Day(){
        long tenDaysBefore = DateProvider.getInstance().now().getTime() - 10*(24 * 60 * 60 * 1000);
        Transaction transaction = new Transaction(1000.0,new Date(tenDaysBefore));
        Account savingsAccount = new Account(Account.MAXI_SAVINGS);
        savingsAccount.transactions.add(transaction);
        Customer john = new Customer("John").openAccount(savingsAccount);
        assertEquals(50.0, john.totalInterestEarned(), DOUBLE_DELTA);       
            
    }

}
