package com.abc;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class DateProviderTest {
    // Manually adjusts date and tests older than function.
    @Test
    public void changeDateTest(){
        DateProvider date = new DateProvider();
        LocalDate falseDate = LocalDate.of(2018, 10, 13);
        LocalDate trueDate = LocalDate.of(1999, 10, 13);
        assertFalse(date.isOlderThan(falseDate, 10));
        assertTrue(date.isOlderThan(trueDate, 10));
    }

    // Tests same day function.
    @Test
    public void sameDayTest(){
        DateProvider date = new DateProvider();
        LocalDate date1 = LocalDate.of(2018, 10, 13);
        LocalDate date2 = LocalDate.of(2018, 10, 13);
        assertTrue(date.isSameDate(date1, date2));
    }
}
