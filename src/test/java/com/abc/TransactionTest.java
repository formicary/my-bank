package com.abc;

import com.abc.bank.Transaction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testTransactionConstructor() {
        Transaction transaction = new Transaction(5);

        assertEquals(5, transaction.getAmount(), DOUBLE_DELTA);
    }
}
