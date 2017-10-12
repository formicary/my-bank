package com.abc;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void canInitialisedTransaction() {
        Date d = DateProvider.getInstance().now();
        Transaction t = new Transaction(22.7, d);
        assertEquals(22.7, t.getAmount(), DOUBLE_DELTA);
        assertTrue(d.compareTo(t.getTransactionDate()) == 0);
    }
}
