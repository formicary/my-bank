package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test (expected = Exception.class)
    public void testDepositNegativeAmount() {
        Account a = new SavingsAccount();
        a.deposit(-100);
    }
    
    @Test (expected = Exception.class)
    public void testWithdrawNegativeAmount() {
        Account a = new SavingsAccount();
        a.withdraw(-100);
    }
    
    @Test
    public void testCheckingAccountInterest() {
        Account a = new CheckingAccount();
        
        a.deposit(100.0);
        a.deposit(4100.0);
        a.withdraw(200.0);
    
        double expected = 4.0;
        assertEquals(expected, a.interestEarned(), Utils.DOUBLE_DELTA);
    }     
    
    @Test
    public void testSavingsAccountInterest() {
        Account a = new SavingsAccount();
        
        a.deposit(100.0);
        a.deposit(4100.0);
        a.withdraw(200.0);
    
        double expected = 7.01;
        assertEquals(expected, a.interestEarned(), Utils.DOUBLE_DELTA);
    }    
    
    @Test
    public void testMaxiSavingsAccountInterest() {
        Account a = new MaxiSavingsAccount();
        
        a.deposit(100.0);
        a.deposit(2500.0);
        a.withdraw(200.0);
    
        double expected = 2.4;
        
        assertEquals(expected, a.interestEarned(), Utils.DOUBLE_DELTA);
    }
    
    @Test
    public void testMaxiSavingsAccountInterestNoWithdraw() {
        Account a = new MaxiSavingsAccount();
        
        a.deposit(4000.0);
    
        double expected = 205.07;
        assertEquals(expected, a.interestEarned(), Utils.DOUBLE_DELTA);
    }   
    
    @Test
    public void testNoTransaction() {
        Account a = new CheckingAccount();

        double expected = 0;
        
        assertEquals(expected, a.interestEarned(), Utils.DOUBLE_DELTA);
    }   
    
    @Test
    public void testGetAccountType1() {
        Account a = new MaxiSavingsAccount();
        assertEquals("Maxi Savings Account", a.getAccountType());
    }
    
    @Test
    public void testGetAccountType2() {
        Account a = new CheckingAccount();
        assertEquals("Checking Account", a.getAccountType());
    }
    
}
