package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    
    private static final double DOUBLE_DELTA = 1e-15;
    
    // deposit() and withdraw() tests
    
    @Test(expected = IllegalArgumentException.class)
    public void negativeDeposit() {
        Account checkAcc = new Account(Account.CHECKING);
        checkAcc.deposit(-100.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void negativeWithdrawal() {
        Account checkAcc = new Account(Account.CHECKING);
        checkAcc.withdraw(-100.0);
    }
    
    // sumTransactions() tests
    
    @Test
    public void testDeposit() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(100.0);
        assertEquals(100.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
        
    @Test
    public void noTransactions() {
        Account checkAcc = new Account(Account.CHECKING);
        assertEquals(0, checkAcc.sumTransactions(), DOUBLE_DELTA);
    }
    
    // getAccountType() tests
    
    @Test
    public void testAccType() {
        Account checkAcc = new Account(Account.CHECKING);
        assertEquals(0, checkAcc.getAccountType(), DOUBLE_DELTA);
    }
    
    // interestEarned() tests
    
    @Test
    public void checkingInterest() {
        Account checkAcc = new Account(Account.CHECKING);
        checkAcc.deposit(1200.0);
        checkAcc.withdraw(200.0);
        assertEquals(1, checkAcc.interestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void savingsInterest01() {
        Account saveAcc = new Account(Account.SAVINGS);
        saveAcc.deposit(900.0);
        assertEquals(0.9, saveAcc.interestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void savingsInterest02() {
        Account saveAcc = new Account(Account.SAVINGS);
        saveAcc.deposit(2000.0);
        assertEquals(3, saveAcc.interestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxiInterest2() {
        Account maxiAcc = new Account(Account.MAXI_SAVINGS);
        maxiAcc.deposit(900.0);
        assertEquals(18, maxiAcc.interestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxiInterest5() {
        Account maxiAcc = new Account(Account.MAXI_SAVINGS);
        maxiAcc.deposit(1500.0);
        assertEquals(45, maxiAcc.interestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxiInterest10() {
        Account maxiAcc = new Account(Account.MAXI_SAVINGS);
        maxiAcc.deposit(3000.0);
        assertEquals(170, maxiAcc.interestEarned(), DOUBLE_DELTA);
    }
   
    @Test
    public void negativeInterest() {
        Account checkAcc = new Account(Account.CHECKING);
        checkAcc.withdraw(100.0);
        assertEquals(0, checkAcc.interestEarned(), DOUBLE_DELTA);
    }
}
