package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

// Todo: write meaningful tests
public class TransactionTest {
    @Test
    public void transaction() {
        BigDecimal amount = BigDecimal.valueOf(5.00);
        Transaction t = new Transaction(amount);
        assertTrue(t instanceof Transaction);
    }
}
