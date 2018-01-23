package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    
    private static final double DOUBLE_DELTA = 1e-15;
    
    //test the SAVINGS account type interest calculation
    @Test
    public void testInterestEarnedSavings(){
        Account ac1 = new Account(Account.SAVINGS);
        ac1.deposit(3000);
        assertEquals(5, ac1.interestEarned(),DOUBLE_DELTA);
    }
    
    //test the CHECKING account type interest calculation
    @Test
    public void testInterestEarnedChecking() {
        Account ac1 = new Account(Account.CHECKING);
        ac1.deposit(3000);
        assertEquals(3, ac1.interestEarned(),DOUBLE_DELTA);
    }
    
    //test the MAXI_SAVINGS account type interest calculation
    @Test
    public void testInterestEarnedMaxiSaving() {
        Account ac1 = new Account(Account.MAXI_SAVINGS);
        ac1.deposit(3000);
        assertEquals(150, ac1.interestEarned(),DOUBLE_DELTA);
    }
    
    //test the sumTransactions() method over no transactions
    @Test
    public void testSumTransactionsNone() {
        Account ac1 = new Account(Account.SAVINGS);
        assertEquals(0, ac1.sumTransactions(),DOUBLE_DELTA);
    }
    
    //test the sumTransactions() method over three transactions
    @Test
    public void testSumTransactionsMore() {
        Account ac1 = new Account(Account.CHECKING);
        ac1.deposit(300);
        ac1.deposit(4500);
        ac1.withdraw(3000);
        assertEquals(1800,ac1.sumTransactions(),DOUBLE_DELTA);
    }
}
