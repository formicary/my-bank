package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void deposit_test() {
        Account savingsAccount = new Account(Account.SAVINGS);
        savingsAccount.deposit(4000.0);
        savingsAccount.deposit(200.0);
        savingsAccount.deposit(1.0);
        
        double savingsAccountBalance = savingsAccount.sumTransactions();

        assertEquals(4201.0, savingsAccountBalance, DOUBLE_DELTA);
    }
    
    @Test
    public void withdraw_test() {
        Account savingsAccount = new Account(Account.SAVINGS);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        savingsAccount.withdraw(1.0);
        
        double savingsAccountBalance = savingsAccount.sumTransactions();

        assertEquals(3799.0, savingsAccountBalance, DOUBLE_DELTA);
    }
    
    @Test
    public void get_account_type() {
        Account savingsAccount = new Account(Account.SAVINGS);

        assertEquals(1, savingsAccount.getAccountType());
    }
    
    @Test
    public void test_interest_earned() {
    	Account savingsAccount = new Account(Account.MAXI_SAVINGS);
        savingsAccount.deposit(500.0);
        
        double interestEarned = savingsAccount.interestEarned();

        assertEquals(10.0, interestEarned, DOUBLE_DELTA);
    }



}
