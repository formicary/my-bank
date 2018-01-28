package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;
    @Test
    public void testTransactionAmount() {
        Transaction t = new Transaction(5.00);
        assertEquals(5.00,t.getAmount(),DOUBLE_DELTA);
    }
}
