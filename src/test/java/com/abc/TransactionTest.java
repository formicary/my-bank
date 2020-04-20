package com.abc;

import com.abc.transaction.Transaction;
import com.abc.transaction.TransactionType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void transactionWithdrawal() {
        // when
        Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, 5);

        // than
        assertEquals(TransactionType.WITHDRAWAL, transaction.getTransactionType());
        assertEquals(5, transaction.getAmount(), DOUBLE_DELTA);
    }

    @Test
    public void transactionDeposit() {
        // when
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, 5);

        // than
        assertEquals(TransactionType.DEPOSIT, transaction.getTransactionType());
        assertEquals(5, transaction.getAmount(), DOUBLE_DELTA);
    }
}
