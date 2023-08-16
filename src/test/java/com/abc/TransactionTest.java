package com.abc;

import org.junit.Test;

import com.abc.DateUtils.DateProvider;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testValidTransactionInstance() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void testGetTransactionAmount() {
        Transaction t = new Transaction(5);
        assertEquals(5, t.getTransactionAmount(), DOUBLE_DELTA);
    }

    @Test
    public void testGetTransactionDate() {
        Transaction t = new Transaction(5);
        assertTrue(t.getTransactionDate() instanceof Date);
    }

    @Test
    public void testisDateAfter() {
        Transaction t = new Transaction(5);
        assertFalse(t.isAfter(DateProvider.getInstance().now()));
    }
}
