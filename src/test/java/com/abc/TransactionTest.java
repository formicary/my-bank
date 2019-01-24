package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class TransactionTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
    @Test
    public void transactionTest() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
		assertEquals(t.getAmount(), 5.0, DOUBLE_DELTA);
    }
}
