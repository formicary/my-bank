package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testTransaction() {
        Transaction t = new Transaction(5.0);

        assertTrue(t instanceof Transaction);
    }

    @Test
    public void testTransactionAccount() {
        Transaction t = new Transaction(10.0);

        assertEquals(10.0, t.getTransactionAmount(), DOUBLE_DELTA);
    }

    @Test
    public void testTransactionType_PosAmount() {
        Transaction t = new Transaction(100.0);

        assertEquals("Deposit", t.getTransactionType());
    }

    @Test
    public void testTransactionType_NegAmount() {
        Transaction t = new Transaction(-50.0);

        assertEquals("Withdrawal", t.getTransactionType());
    }
}
