package com.accenture.mybank.tests;

import org.junit.Test;

import com.accenture.mybank.Transaction;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
}
