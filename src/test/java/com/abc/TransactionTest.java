package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void testTransaction() {
        Transaction t = new Transaction(5);

        assertTrue(t instanceof Transaction);
    }

    @Test
    public void testTransactionDate() {
        Transaction t = new Transaction(5);

        assertEquals(DateProvider.getInstance().now().toString(), t.getDate().toString());
    }
}
