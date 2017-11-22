package com.abc;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void getAmountRetrieveRightAmount() {
        Transaction transaction = new Transaction(10);

        assertEquals(10, transaction.getAmount(), DOUBLE_DELTA);
    }

}
