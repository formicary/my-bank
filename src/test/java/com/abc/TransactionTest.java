package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(BigDecimal.valueOf(5));
        assertTrue(t instanceof Transaction);
    }
}
