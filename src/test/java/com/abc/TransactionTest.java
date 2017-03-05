package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.GregorianCalendar;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
    @Test
    public void dateTest() {
    	Transaction t = new Transaction(2);
    	Date date = new GregorianCalendar(2017, 2, 3).getTime();
    	t.setTransactionDate(date);
    	assertEquals(t.getTransactionDate(), date);
    }
}
