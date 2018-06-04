package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is for testing if a transaction can be created successfully.
 * @author Peng Shao. Modifed based on the exercise provided by Accenture.
 * @version  03/05/2018
 */
public class TransactionTest {

    @Test
    /**
     * This is to test is a transaction can be created successfully.
     */
    public void transaction() {
        Transaction t = new Transaction(new BigDecimal(5));
        assertTrue(t instanceof Transaction);
    }

    @Test
    /**
     * This is to test the transactionDatePlus10Days method
     */
    public void testTransactionPlus10Days() {
        Transaction t = new Transaction(new BigDecimal(5));
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(DateProvider.getInstance().now());
        cal.add(Calendar.DATE, 10);
        t.setTransactionDate(t.getTransactionDatePlus10Days());

        assertEquals(cal.getTime().toString(), t.getTransactionDate().toString());
    }
}
