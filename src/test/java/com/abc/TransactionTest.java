package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    private static final double DOUBLE_DELTA = 1e-10;

    /**
     * Check that amounts of transactions are correct
     */
    @Test
    public void transactionAmount() {
        Transaction t = new Transaction("Test", 5);
        assertEquals(5, t.getAmount(), DOUBLE_DELTA);

        t = new Transaction("Test", -2.34);
        assertEquals(-2.34, t.getAmount(), DOUBLE_DELTA);
    }
}
