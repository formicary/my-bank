package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    @Test
    public void getAmount() {
        Transaction t = new Transaction(5);
        assertEquals(5.0, t.getAmount());
    }
}
