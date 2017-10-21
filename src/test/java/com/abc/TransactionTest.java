package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void testTransactionType(){
        Transaction deposit = new Transaction(50);
        assertEquals(1, deposit.getTransactionType());

        Transaction withdraw = new Transaction(-50);
        assertEquals(0, withdraw.getTransactionType());
    }

}