package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the transaction class
 */
public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    /**
     * Test setting the amount of transaction
     */
    @Test
    public void transaction() {
        Transaction transaction = new Transaction(5);
        assertEquals(transaction.getAmount(), 5, DOUBLE_DELTA);
    }
}
