package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    /**
     * Testing the ability to accurately reset the date of a transaction.
     */
    @Test
    public void testDateReset() {
        Date christmas2018 = new GregorianCalendar(2018, Calendar.DECEMBER, 25).getTime();

        Transaction netflix = new Transaction(11.99);
        netflix.setTransactionDate(christmas2018);

        assertEquals(christmas2018, netflix.getTransactionDate());
    }
}
