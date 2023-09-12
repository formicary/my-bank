package com.mybank;

import com.mybank.Utlities.TransactionType;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    private Transaction transaction;

    @Test
    public void transaction() {
        givenTransaction();

        thenIsInstanceOfTransaction();
    }

    private void givenTransaction() {
        transaction = new Transaction(5.0, TransactionType.DEPOSIT);
    }

    private void thenIsInstanceOfTransaction() {
        assertNotNull(transaction);
    }
}
