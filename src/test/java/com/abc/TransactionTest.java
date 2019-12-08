package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Transaction class
 */
public class TransactionTest {

    private static final double DOUBLE_DELTA = 1e-15;

    /**
     * Test setting the amount of a transaction
     */
    @Test
    public void testTransactionAmount() {
        Transaction transaction = new Transaction(5);
        assertEquals(transaction.getAmount(), 5, DOUBLE_DELTA);
    }

}
