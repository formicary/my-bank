package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    @Test
    public void transactionAmountInit() {
        Transaction t = new Transaction(5);
        assertEquals(t.getAmount(), 5);
    }
}
