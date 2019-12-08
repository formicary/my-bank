package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests for the Transaction class
 */
public class TransactionTest {

    @Test
    /**
     * Test creating a transaction
     */
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

}
