package com.abc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TransactionTest {

	@Test
    public void testTransactionAmount() {
    	double transactionAmount = 10;
    	Transaction t = new Transaction(transactionAmount);
    	assertTrue("Transaction instance has been created with the correct amount being saved"
    			,transactionAmount == t.amount);
    }

}
