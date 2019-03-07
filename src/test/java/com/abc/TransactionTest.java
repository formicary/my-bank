package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TransactionTest {

	@Test
    public void transactionAmount() {
    	double transactionAmount = 10;
    	Transaction t = new Transaction(transactionAmount);
    	assertEquals("Transaction instance has been created with the correct amount being saved"
    			,transactionAmount, t.amount, ConstantsTest.DOUBLE_DELTA);
    }

}
