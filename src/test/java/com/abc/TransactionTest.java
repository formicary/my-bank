package com.abc;

import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class TransactionTest {
    @Test // Test transaction
    public void testTransaction() {
        Transaction transaction = new Transaction(500.0);
        assertTrue(transaction instanceof Transaction);
    }

    @Ignore // Test transaction date
    public void testTransactionDate() {
        Transaction transaction = new Transaction(500.0);
        assertEquals(DateProvider.getInstance().now(), transaction.transactionDate);
    }
}