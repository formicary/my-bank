package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TransactionShould {
    @Test
    public void CreateATransactionWithTheExpectedValue() {
        int transactionAccount = 5;

        Transaction transaction = new Transaction(transactionAccount);

        assertEquals(transactionAccount, transaction.amount, 0);
    }
}
