package com.abc;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class DateProviderTest {

    // Checks whether two dates are older than date a mocked present date.
    @Test
    public void changeDateTest(){
        DateProvider date = new DateProvider();
        date.setDate(LocalDate.of(2018, 10, 18));
        LocalDate falseDate = LocalDate.of(2018, 10, 13);
        LocalDate trueDate = LocalDate.of(1999, 10, 13);
        assertFalse(date.isOlderThan(falseDate, 10));
        assertTrue(date.isOlderThan(trueDate, 10));
    }

    // Tests whether two dates are the same.
    @Test
    public void sameDayTest(){
        DateProvider date = new DateProvider();
        LocalDate date1 = LocalDate.of(2018, 10, 13);
        LocalDate date2 = LocalDate.of(2018, 10, 13);
        assertTrue(DateProvider.isSameDate(date1, date2));
    }
}
