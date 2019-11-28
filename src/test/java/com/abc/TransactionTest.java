package com.abc;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    private static final double DELTA = 1e-15;

    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    // Test retrieval of data
    @Test
    public void TestTransaction() {
        Transaction testTransaction = new Transaction(2000);

        // Retrieve amount
        assertEquals(2000, testTransaction.getAmount(), DELTA);
        // Get transaction ID
        assertEquals(0, testTransaction.getTransactionId());
        // Get transaction type
        assertEquals("Deposit", testTransaction.getTransactionType());
        // Get date
        System.out.println("Date: " + testTransaction.getTransactionDate());
    }
}
