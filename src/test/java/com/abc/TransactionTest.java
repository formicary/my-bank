package com.abc;

import com.abc.MainClasses.Transaction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//Added test to validate getAmount method
public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        
        assertTrue(t instanceof Transaction);
        assertEquals("5.0", String.valueOf(t.getAmount()));
    }
}