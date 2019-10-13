package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(new BigDecimal("100.21"));

        assertEquals(1, t.getAmount().signum());
        assertEquals(100, t.getAmount().intValue());
        assertEquals("100.21", t.getAmount().toString());
    }
}
