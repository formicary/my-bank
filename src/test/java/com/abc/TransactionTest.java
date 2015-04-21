package com.abc;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void transactionDetailsPast() throws ParseException {

        //Set up fake date to test with
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        Date date = dateFormat.parse("Apr 14, 2015 12:00 PM");
        DateProvider.setDate(date);

        Transaction t = new Transaction(5);
        assertEquals(5, t.amount, DOUBLE_DELTA);
        assertEquals("Tue Apr 14 12:00:00 EDT 2015", t.transactionDate.toString());

        //Reset fake date for other test cases
        DateProvider.resetDate();
    }

    @Test
    public void transactionDetailsNow() {

        Transaction t = new Transaction(5);
        assertEquals(5, t.amount, DOUBLE_DELTA);
        assertEquals(Calendar.getInstance().getTime(), t.transactionDate);
    }




}
