package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transactionTenCents() {
        Transaction t = new Transaction(10);
        assertEquals(10, t.cents);
    }
}
