package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(BigDecimal.valueOf(5), "withdrawal");
        assertEquals(DateProvider.now(), t.getTransactionDate());
        assertEquals("withdrawal", t.getTransactionType());
        assertEquals(0, t.amount.compareTo(BigDecimal.valueOf(5)));
    }
}


