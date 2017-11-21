package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5,true);
        assertTrue(t instanceof Transaction);
        assertEquals(true, t.getType());
        assertEquals(5.0, t.getAmount(), 0.001);
    }
}
