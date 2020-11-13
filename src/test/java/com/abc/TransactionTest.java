package com.abc;

import com.abc.core.account.Transaction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionTest {

    private static final double DELTA = 1e-15;

    @Test
    public void transactionIsCreatedCorrectly() {
        Transaction transaction = new Transaction(5.0);
        assertEquals(5.0, transaction.getAmount(), DELTA);
        assertNotNull(transaction.getTransactionDate());
    }

    @Test
    public void transactionWithNegativeAmountCanBeCreated() {
        Transaction transaction = new Transaction(-20.0);
        assertNotNull(transaction);
        assertEquals(-20.0, transaction.getAmount(), DELTA);
    }
}
