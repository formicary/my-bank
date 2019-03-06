package com.abc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TransactionTest {
	
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

	@Test
    public void testTransactionAmount() {
    	double transactionAmount = 10;
    	Transaction t = new Transaction(transactionAmount);
    	assertTrue(transactionAmount == t.amount);
    }
}
