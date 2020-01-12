package com.abc;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import com.abc.accounts.*;

public class DateProviderTest {

    private DateProvider dateProvider;
    
    @Before
    public void setup(){
        dateProvider = DateProvider.getInstance();
    }

    @Test
    public void getNumberOfElapsedDays(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -8);
        
        Date newDate = calendar.getTime();
        dateProvider.setStartingDate(newDate);

        assertEquals(8, dateProvider.getNumberOfElapsedDays());
    }
}