package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transactionAmount() {
        Transaction t = new Transaction(5);

        // Test type of t
        assertTrue(t instanceof Transaction);

        assertTrue(t.getAmount() == 5);

        // Boundary case, should be true
        assertTrue(t.getAmount() == 5.0);

        assertFalse(t.getAmount() != 5);

        assertFalse(t.getAmount() == 'c');
    }

    @Test
    public void transactionTime(){

        Transaction t = new Transaction(5);
        long dateDiff = Math.abs(t.getDate().getTime() - Calendar.getInstance().getTime().getTime());

        assertTrue((TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS) == 0));

        // Boundary case, should be false as this should be a long, not a double
        assertTrue((TimeUnit.DAYS.convert(dateDiff, TimeUnit.MILLISECONDS) == 0.0));

    }
}
