package com.abc;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void transactionDeposit() {
        Date date = new Date();

        Transaction t = new Transaction(5, date);
        assertTrue(t instanceof Transaction);

        assertEquals(5.0, t.getAmount(), DOUBLE_DELTA);
        assertEquals(Transaction.TransactionType.DEPOSIT, t.getTransactionType());
        assertEquals(date, t.getTransactionDate());
    }

    @Test
    public void transactionWithdrawal() {
        Date date = new Date();

        Transaction t = new Transaction(-5, date);
        assertTrue(t instanceof Transaction);

        assertNotEquals(5.0, t.getAmount(), DOUBLE_DELTA);
        assertNotEquals(Transaction.TransactionType.DEPOSIT, t.getTransactionType());
        assertEquals(date, t.getTransactionDate());
    }

}
