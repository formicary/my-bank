package com.abc;
import org.junit.Test;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertNotEquals;


public class DateProviderTest {

    @Test
    public void dateTest(){
        DateProvider date = new DateProvider();
        Date trueDate = new GregorianCalendar(2018, Calendar.JANUARY, 20).getTime();
        Date falseDate = new GregorianCalendar(2018, Calendar.OCTOBER, 13).getTime();
        assertNotEquals(date.tenDayCheck(trueDate), true);
        assertNotEquals(date.tenDayCheck(falseDate), false);
    }
}
