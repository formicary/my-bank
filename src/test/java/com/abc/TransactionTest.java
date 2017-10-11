package com.abc;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void isTransactionTypeWhenInitialised() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void isAmountCorrectWhenInitialised() {
        Transaction t = new Transaction(22.7);
        assertEquals(22.7, t.getAmount(), DOUBLE_DELTA);
    }

    @Test
    //Not entirely sure how to test exact time as we cannot pass
    //Date at initialisation otherwise transaction object can be
    //Created at a different time then 'now'.
    public void isTransactionDateSetWhenInitialised() {
        Transaction t = new Transaction(2);
        assertNotNull(t.getTransactionDate());
        assertTrue(t.getTransactionDate() instanceof Date);
    }
}
