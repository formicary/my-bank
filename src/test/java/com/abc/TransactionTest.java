package com.abc;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void transaction_time(){
        Transaction t = new Transaction(3);
        t.setTime(new Date(1990, 12, 25));
        assertEquals(new Date(1990, 12, 25), t.timestamp());
    }

    @Test
    public void transaction_fail(){
        Transaction t = null;
        assertFalse(t instanceof Transaction);
    }
}
