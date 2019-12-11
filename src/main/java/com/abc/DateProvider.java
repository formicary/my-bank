package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateProvider {
   
    public DateProvider() {	 
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
   
}
