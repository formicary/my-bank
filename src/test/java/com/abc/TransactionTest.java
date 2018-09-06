package com.abc;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void CreditTransaction() {
        Transaction t = new CreditTransaction(5);
        assertEquals(t.amount, 5.0, DOUBLE_DELTA);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void debitTransaction() {
        Transaction t = new DebitTransaction(20);
        assertEquals(t.amount, -20.0, DOUBLE_DELTA);
    }
}
