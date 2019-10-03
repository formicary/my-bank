package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    @Test
    public void testDeposit() {
        Transaction t = new Transaction(5);
        assertEquals("deposit $5.00", t.getTransactionDetails());
    }

    @Test
    public void testWithdrawal() {
        Transaction t = new Transaction(-5);
        assertEquals("withdrawal $5.00", t.getTransactionDetails());
    }
}
