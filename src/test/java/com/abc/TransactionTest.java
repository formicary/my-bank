package com.abc;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * This class contains unit tests for the Transaction class.
 * These tests cover various aspects of transaction functionality.
 */
public class TransactionTest {

    /**
     * Tests creating a new transaction.
     */
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    /**
     * Tests getting the amount of a transaction.
     */
    @Test
    public void getAmount() {
        Transaction t = new Transaction(10.0);
        assertEquals(10.0, t.getAmount(), 0.001);
    }

    /**
     * Tests getting the transaction date of a transaction.
     */
    @Test
    public void getTransactionDate() {
        Transaction t = new Transaction(15.0);
        assertNotNull(t.getTransactionDate());
    }

    /**
     * Tests the consistency of transaction dates.
     */
    @Test
    public void transactionDateConsistency() {
        Transaction t = new Transaction(20.0);
        Date initialDate = t.getTransactionDate();

        // Simulate some delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Date newDate = t.getTransactionDate();
        assertEquals(initialDate, newDate);
    }
}
