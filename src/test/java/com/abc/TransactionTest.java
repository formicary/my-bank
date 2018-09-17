package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
	
    @Test
    public void transaction() {
        Transaction deposit = new Transaction(5, Transaction.DEPOSIT);
        assertTrue(deposit instanceof Transaction);
    }
    
    @Test
    public void transferTransaction() {
    	Account savingsAccount = new Account(Account.SAVINGS);
    	
    	Transaction transfer = new Transaction(5, savingsAccount);
    	assertTrue(transfer instanceof Transaction);
    }
}
