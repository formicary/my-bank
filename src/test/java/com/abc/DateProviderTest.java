
package com.abc;

import org.junit.Assert;
import org.junit.Test;
import java.util.Date;

public class DateProviderTest {

    @Test
    public void getDateFromPastTestOne(){
        DateProvider date = new DateProvider();

        Date now = date.now();

        Date past = date.getDateFromPast(10);
        Date dateToCheck = date.getDateFromPast(5);

        //This checks if the date 5 days ago is between now and 10 days ago.
        boolean sample = (!((dateToCheck.before(past))
                || (dateToCheck.after(now)))) ? true : false;

        //true if the date is within range and false if not
        Assert.assertEquals(sample, true);
    }

    @Test
    public void getDateFromPastTestTwo(){
        DateProvider date = new DateProvider();

        Date now = date.now();
        Date dateToCheck = date.getDateFromPast(10);
        Date past = date.getDateFromPast(5);

        //This checks if the date 10 days ago is between now and 5 days ago.
        boolean sample = (!((dateToCheck.before(past))
                || (dateToCheck.after(now)))) ? true : false;

        //true if the date is within range and false if not
        Assert.assertEquals(sample, false);
    }

    @Test
    public void getDateFromPastTestThree(){
        DateProvider date = new DateProvider();

        Date now = date.now();
        Date dateToCheck = date.getDateFromPast(5);
        Date past = date.getDateFromPast(5);

        //This checks if the date 5 days ago is between now and 5 days ago.
        //true if the date is within range and false if not

        boolean sample = (!((dateToCheck.before(past))
                || (dateToCheck.after(now)))) ? true : false;

        //true if the date is within range and false if not
        Assert.assertEquals(sample, true);
    }
}
