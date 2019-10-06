package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

//todo what's the point in this test? How can it actually be tested better?
public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;
    @Test
    public void transaction() {
        Transaction t = new Transaction(5, "withdrawal");
        assertEquals(DateProvider.now(), t.getTransactionDate());
        assertEquals("withdrawal", t.getTransactionType());
        assertEquals(5, t.amount, DOUBLE_DELTA);
    }
}


