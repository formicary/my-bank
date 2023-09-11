package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    private Transaction t;

    @Test
    public void transaction() {
        givenTransaction();

        thenIsInstanceOfTransaction();
    }

    private void givenTransaction() {
        t = new Transaction(5, TransactionType.DEPOSIT);
    }

    private void thenIsInstanceOfTransaction() {
        assertTrue(t instanceof Transaction);
    }
}
