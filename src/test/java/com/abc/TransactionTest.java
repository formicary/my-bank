package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import com.abc.Transaction.TransactionType;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testTransactionAmount() {
        Transaction t = new Transaction(100.0, TransactionType.DEPOSIT);

        assertEquals(100.0, t.getAmount(), DOUBLE_DELTA);
    }

    @Test
    public void testTransactionType() {
        Transaction t = new Transaction(100.0, TransactionType.TRANSFER_IN);

        assertEquals(Transaction.TransactionType.TRANSFER_IN, t.getTransactionType());
    }

    @Test
    public void testTransactionDate() {
        Date now = Calendar.getInstance().getTime();
        DateProvider.getInstance().setCustomDate(now);

        Transaction t = new Transaction(100.0, TransactionType.DEPOSIT);

        if (!t.getDate().equals(now)) {
            fail();
        }
    }
}
