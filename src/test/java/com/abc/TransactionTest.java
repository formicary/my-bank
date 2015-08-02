package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
	private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
        assertEquals(t.amount, 5, DOUBLE_DELTA);
    }
}
