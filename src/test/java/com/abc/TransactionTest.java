package com.abc;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
    @Test
    public void transaction() {
        Transaction t = new Transaction(5, Transaction.DEPOSIT);
        assertTrue(t instanceof Transaction);
    }
    
    @Test
    public void checkAmount() {
    	Transaction t = new Transaction(10, Transaction.DEPOSIT);
    	double a = t.getAmount();
    	assertEquals(10, a, DOUBLE_DELTA);
    }
}
