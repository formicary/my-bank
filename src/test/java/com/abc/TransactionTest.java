package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertEquals(5, t.amount, 1e-15);
        assertNotNull(t.transactionDate);
    }
}
