package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.Bank.Transaction;

public class TransactionTest {
    @Test
    public void depositTest() {
        Transaction t = new Transaction(5);
        assertEquals("deposit $5.00", t.getDetails());
    }

    @Test
    public void withdrawalTest() {
        Transaction t = new Transaction(-5);
        assertEquals("withdrawal $5.00", t.getDetails());
    }
}
