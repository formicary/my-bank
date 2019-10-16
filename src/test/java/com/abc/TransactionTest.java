package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        BigDecimal five = new BigDecimal(5);
        Transaction t = new Transaction(five, Transaction.AccountOperation.WITHRAW);
        assertTrue(t instanceof Transaction);
    }
}
