package com.abc;


import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5.5);
        assertTrue(t instanceof Transaction);
        assertEquals(t.getAmount(),5.5);
        assertEquals(t.transactionType(),"deposit");
    }
}