package com.abc;
import org.junit.Test;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;


public class DateProviderTest {

    @Test
    public void dateTest(){
        DateProvider date = new DateProvider();
        Date falseDate = new GregorianCalendar(2018, Calendar.OCTOBER, 13).getTime();
        Date trueDate = new GregorianCalendar(1999, Calendar.OCTOBER, 13).getTime();
        assertTrue(date.tenDayCheck(falseDate));
        assertFalse(date.tenDayCheck(trueDate));
    }
}
