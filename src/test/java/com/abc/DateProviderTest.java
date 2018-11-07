package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class DateProviderTest {

    @Test
    public void currentDateTest() {
        try {
            Date start = Calendar.getInstance().getTime();
            Thread.sleep(1000);

            Date provider = DateProvider.getInstance().now();

            Thread.sleep(1000);
            Date end = Calendar.getInstance().getTime();
            assertTrue(provider.after(start));
            assertTrue(provider.before(end));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void backInTimeTest(){
        Date now = DateProvider.getInstance().now();
        Date then = DateProvider.getInstance().getPreviousDay(10);
        long difference = now.getTime() - then.getTime();
        assert ((86400000 * 10) - 200 < difference
                & difference < (86400000 * 10) + 200); // needs some leeway as it uses current time
    }

}