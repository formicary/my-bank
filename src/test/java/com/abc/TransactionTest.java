package com.abc;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    private Date currentDate = DateProvider.getInstance().now();
    @Test
    public void transaction() {
        Transaction t = new Transaction(5,currentDate);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void transactionTypes() {
        Transaction t1 = new Transaction(-5, currentDate);
        Transaction t2 = new Transaction(5, currentDate);
        assertTrue(t1.transactionType());
        assertFalse(t2.transactionType());
    }
}
