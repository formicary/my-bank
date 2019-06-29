package com.abc;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManagerTest {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void getDateUnder10Days(){

        Date d = null, d1 = null;

        try {
            d = simpleDateFormat.parse("2019-05-05 01:00:00");
            d1 = simpleDateFormat.parse("2019-05-07 23:00:00");
        } catch (ParseException e) {
            Assert.fail("error: failed to parse dates");
        }

        Assert.assertEquals(2, DateManager.daysDifference(d, d1));


    }

    @Test
    public void getDateOver10Days(){
        Date d = null, d1 = null;

        try {
            d = simpleDateFormat.parse("2018-05-05 00:00:00");
            d1 = simpleDateFormat.parse("2019-05-05 23:59:59");
        } catch (ParseException e) {
            Assert.fail("error: failed to parse dates");
        }

        Assert.assertEquals(365, DateManager.daysDifference(d, d1));
    }

}
