package com.abc;

import java.util.Date;

import main.java.com.abc.DateProvider;
import main.java.com.abc.Transaction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5, true);
        assertTrue(t instanceof Transaction);
    }
    
    /**
     * Testing if the transaction date is set correctly and getTransactionDate() works.
     */
    @Test
    public void transactionDate() {
    	Date transactionDate = DateProvider.getInstance().now();
        Transaction t = new Transaction(5, true);
        assertEquals(transactionDate.toString(), t.getTransactionDate().toString());
    }
    
    /**
     * Testing if the transaction type gets set correctly.
     */
    @Test
    public void transactionType() {
        Transaction t = new Transaction(5, true);
        assertEquals(true, t.getType());
    }
    
    /**
     * Testing if the transaction type gets set correctly.
     */
    @Test
    public void transactionAmount() {
        Transaction t = new Transaction(5, true);
        assertEquals(5.0, t.getAmount(), 0.001);
    }
}

