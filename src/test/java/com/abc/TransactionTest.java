package com.abc;

import com.abc.Utilities.TestingDateProvider;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transactionTest() {
        TestingDateProvider.getInstance().setTestingModeOff();
        Date start = Calendar.getInstance().getTime();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Transaction t = new Transaction(5.0, Transaction.TransctionType.DEPOSIT, DateProvider.getInstance().now());
        assertEquals(Transaction.TransctionType.DEPOSIT, t.type);
        assertEquals(5.0, t.amount, 1e-15);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date end = Calendar.getInstance().getTime();
        System.out.printf("%s - %s - %s",start.toString(),t.transactionDate.toString(),end.toString());
        assertTrue(start.before(t.transactionDate));
        assertTrue(end.after(t.transactionDate));

    }
}
