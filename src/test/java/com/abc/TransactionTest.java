package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(new BigDecimal(5), true, Calendar.getInstance());
        assertTrue(t instanceof Transaction);
    }
}
