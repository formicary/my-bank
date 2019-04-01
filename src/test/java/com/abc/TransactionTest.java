package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void checkTypeIfNegative() {
        Transaction t = new Transaction(-5);
        assertTrue(t.getType().equals("withdrawal"));
    }

    @Test
    public void checkTypeIfPositive() {
        Transaction t = new Transaction(5);
        assertTrue(t.getType().equals("deposit"));
    }

}
