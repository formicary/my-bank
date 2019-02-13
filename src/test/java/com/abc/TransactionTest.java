package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test (expected=IllegalArgumentException.class)
    public void Constructor_NegativeAmount() {
        Transaction t = new Transaction(Account.CHECKING, -150, true);
    }

    @Test
    public void TestConstructor_ValidArguments_ShouldReturnTransactionInstance() {
        Transaction t = new Transaction(Account.SAVINGS, 100, false);
        assertTrue(t instanceof Transaction);
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestTransactionDate_NegativeNumberOfDays() {
        Transaction t = new Transaction(Account.SAVINGS, 50, false);
        int daysAgo = -15;
        t.setDateToNDaysAgo((short) daysAgo);
    }

    @Test
    public void TestTransactionDate_PastDate_ShouldUpdateTransactionDateCorrectly() {
        Transaction t = new Transaction(Account.SAVINGS, 50, false);
        GregorianCalendar calendar = new GregorianCalendar();

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int daysAgo = 5;

        t.setDateToNDaysAgo((short) daysAgo);
        calendar.setTime(t.getTransactionDate());

        assertEquals((dayOfYear - daysAgo), calendar.get(Calendar.DAY_OF_YEAR));
    }
}
