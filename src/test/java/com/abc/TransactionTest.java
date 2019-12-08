package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the Transaction class
 */
public class TransactionTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    /**
     * Test setting the amount of a transaction
     */
    public void testTransactionAmount() {
        Transaction transaction = new Transaction(5);
        assertEquals(transaction.getAmount(), 5, DOUBLE_DELTA);
    }

}
