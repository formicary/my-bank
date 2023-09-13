package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains unit tests for the Transaction class.
 * These tests cover various aspects of transaction functionality.
 */
public class TransactionTest {

    private BigDecimal transactionAmount;

    /**
     * Sets up common test objects and actions before each test method.
     */
    @Before
    public void setUp() {
        transactionAmount = new BigDecimal("10.00");
    }

    /**
     * Tests creating a new transaction.
     */
    @Test
    public void createTransaction() {
        Transaction transaction = new Transaction(transactionAmount);
        assertTrue(transaction instanceof Transaction);
    }

    /**
     * Tests getting the amount of a transaction.
     */
    @Test
    public void getTransactionAmount() {
        Transaction transaction = new Transaction(transactionAmount);
        assertEquals(new BigDecimal("10.00"), transaction.getAmount());
    }

    /**
     * Tests getting the transaction date of a transaction.
     */
    @Test
    public void getTransactionDate() {
        Transaction transaction = new Transaction(transactionAmount);
        assertNotNull(transaction.getTransactionDate());
    }

    /**
     * Tests the consistency of transaction dates.
     */
    @Test
    public void transactionDateConsistency() {
        Transaction transaction = new Transaction(transactionAmount);
        LocalDate initialDate = transaction.getTransactionDate();

        // Simulate some delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LocalDate newDate = transaction.getTransactionDate();
        assertEquals(initialDate, newDate);
    }
}
