package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TransactionShould {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void CreateATransactionWithTheExpectedValue() {
        int transactionAccount = 5;

        Transaction transaction = new Transaction(transactionAccount);

        assertEquals(transactionAccount, transaction.amount, DOUBLE_DELTA);
    }
}
