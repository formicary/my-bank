package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testTransaction() {
        Transaction tx = new Transaction(5.0);

        assertEquals(5.0, tx.amount, DOUBLE_DELTA);
    }

    @Test
    public void testGetType() {
        Transaction tx = new Transaction(5.0);
        assertEquals("deposit", tx.getType());

        tx = new Transaction(-5.0);
        assertEquals("withdrawal", tx.getType());
    }

    @Test
    public void testToDollars() {
        String amount = Transaction.toDollars(5.0);
        assertEquals("$5.00", amount);

        amount = Transaction.toDollars(-5.0);
        assertEquals("$5.00", amount);
    }

    @Test
    public void testToString() {
        Transaction tx = new Transaction(5.0);
        assertEquals("deposit $5.00", tx.toString());

        tx = new Transaction(-5.0);
        assertEquals("withdrawal $5.00", tx.toString());
    }

}
