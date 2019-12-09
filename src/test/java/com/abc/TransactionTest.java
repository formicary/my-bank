package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
// Rename to Tests
public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;
    
    @Test
    public void Transaction_WhenCreated_ContainsCorrectAmountAndDateTime(){
        Transaction transaction = new Transaction(50.0);

        assertEquals(50.0, transaction.amount, DOUBLE_DELTA);
        // Test for date/time
    }
}
