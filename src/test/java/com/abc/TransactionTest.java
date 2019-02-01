package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test class for the types of transactions used in the "Transaction", "Customer" and "Account" classes.
 * @author Daniel Seymour
 * @version 1.0
 */
public class TransactionTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	/**
	 * Merely tests whether an instantiation of an object of type (static and dynamic) "Transaction"
	 * is in fact the type "Transaction". 
	 */
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
    
    /**
     * Tests whether the "deposit" and "withdraw" methods work as intended for each account type.
     */
    @Test
    public void depositWithdrawTest() {
    	Account checking = new Checking();
    	Account savings = new Savings();
    	Account maxiSavings = new MaxiSavings();
    	
    	checking.deposit(500.0);
    	checking.withdraw(100.0);
    	savings.deposit(1000.0);
    	savings.withdraw(200.0);
    	maxiSavings.deposit(1500.0);
    	maxiSavings.withdraw(300.0);
    	
    	assertEquals(400.0, checking.sumTransactions(), DOUBLE_DELTA);
    	assertEquals(800.0, savings.sumTransactions(), DOUBLE_DELTA);
    	assertEquals(1200.0, maxiSavings.sumTransactions(), DOUBLE_DELTA);
    }
    
    /**
     * Tests whether the "transferSum" method works from various accounts.
     */
    @Test
    public void transferSumTest() {
    	Account checking = new Checking();
    	Account savings = new Savings();
    	Account maxiSavings = new MaxiSavings();
    	
    	Customer lauren = new Customer("Lauren").openAccount(checking).openAccount(savings).openAccount(maxiSavings);
    	
    	checking.deposit(1000.0);
    	savings.deposit(2000.0);
    	maxiSavings.deposit(3000.0);
    	
    	lauren.transferSum(200.0, checking, savings);
    	assertEquals(800.0, checking.sumTransactions(), DOUBLE_DELTA);
    	assertEquals(2200.0, savings.sumTransactions(), DOUBLE_DELTA);
    	lauren.transferSum(300.0, savings, maxiSavings);
    	assertEquals(1900.0, savings.sumTransactions(), DOUBLE_DELTA);
    	assertEquals(3300.0, maxiSavings.sumTransactions(), DOUBLE_DELTA);
    	lauren.transferSum(400.0, maxiSavings, checking);
    	assertEquals(2900.0, maxiSavings.sumTransactions(), DOUBLE_DELTA);
    	assertEquals(1200.0, checking.sumTransactions(), DOUBLE_DELTA);
    	
    }
    
}