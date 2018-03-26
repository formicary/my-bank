package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void transaction() {
        Transaction t = new Transaction(5, true);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void transactionReturnsCorrectValue(){
        Transaction t = new Transaction(5, true);
        assertEquals(5, t.getTransactionAmount(), DOUBLE_DELTA);
    }

    @Test
    public void transactionIsPositive(){
        Transaction t = new Transaction(5, true);
        assertTrue(t.isDeposit());
    }

    @Test
    public void transactionIsNegative(){
        Transaction t = new Transaction(-5, true);
        assertFalse(t.isDeposit());
    }
}
