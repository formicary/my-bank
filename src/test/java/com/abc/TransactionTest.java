package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest extends TestBase{
    @Test
    public void shouldCreateTransactionWithSpecifiedAmount() {
        assertEquals(5, new Transaction(5).amount, DOUBLE_DELTA);
        assertEquals(-5, new Transaction(-5).amount, DOUBLE_DELTA);
        assertEquals(0, new Transaction(0).amount, DOUBLE_DELTA);
    }
}
