package com.abc;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class DateProviderTest {
    // Manually adjusts date and tests older than function.
    @Test
    public void changeDateTest(){
        DateProvider date = new DateProvider();
        date.setDateTime(LocalDateTime.of(2018, 10, 17, 12, 0,0));
        LocalDateTime falseDate = LocalDateTime.of(2018, 10, 13, 12, 0, 0);
        LocalDateTime trueDate = LocalDateTime.of(1999, 10, 13, 12, 0, 0);
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
