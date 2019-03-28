package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void testTransaction() {
        Transaction t = new Transaction(5, Transaction.DEPOSIT);
        assertTrue(t instanceof Transaction);
    }
}
