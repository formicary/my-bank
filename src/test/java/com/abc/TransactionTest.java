package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void deposit() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
        assertTrue("Deposit".matches(t.getType()));
        assertEquals(5, t.getAmount(), DOUBLE_DELTA);
    }

    @Test
    public void withdrawal() {
        Transaction t = new Transaction(-10);
        assertTrue(t instanceof Transaction);
        assertTrue("Withdrawal".matches(t.getType()));
        assertEquals(-10, t.getAmount(), DOUBLE_DELTA);
    }
}
