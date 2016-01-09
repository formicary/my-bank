package com.abc;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {
    
    private static final double DOUBLE_DELTA = 1e-15;
    
    @Test
    public void testDateDifference() {
        final Date rightNow = DateProvider.getInstance().now();
        final Date tenDaysFromNow = DateProvider.getInstance().now();
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, 10);
        tenDaysFromNow.setTime(now.getTime().getTime());
        assertEquals(10, Utils.dateDifferenceinDays(tenDaysFromNow, rightNow), DOUBLE_DELTA);
    }
    
}
