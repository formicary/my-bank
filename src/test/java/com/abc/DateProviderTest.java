package com.abc;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class DateProviderTest {

    @Test
    public void dateTest(){
        DateProvider date = new DateProvider();
        LocalDate falseDate = LocalDate.of(2018, 10, 13);
        LocalDate trueDate = LocalDate.of(1999, 10, 13);
        assertFalse(date.compareOlderThan(falseDate, 10));
        assertTrue(date.compareOlderThan(trueDate, 10));
    }

    @Test
    public void sameDayTest(){
        DateProvider date = new DateProvider();
        LocalDate date1 = LocalDate.of(2018, 10, 13);
        LocalDate date2 = LocalDate.of(2018, 10, 13);
        assertTrue(date.checkSameDay(date1, date2));
    }
}
