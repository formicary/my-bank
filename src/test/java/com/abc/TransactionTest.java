package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import com.abc.Transaction.TransactionType;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5, TransactionType.INTEREST);
        assertTrue(t instanceof Transaction);
    }
}
