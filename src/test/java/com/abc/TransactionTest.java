package com.abc;

import com.abc.transaction.Transaction;
import com.abc.transaction.TransactionFactory;
import com.abc.transaction.TransactionType;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new TransactionFactory().createTransaction(TransactionType.CREDIT, 5.00);
        assertTrue(t instanceof Transaction);
    }
}
