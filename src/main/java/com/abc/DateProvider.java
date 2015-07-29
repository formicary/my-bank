package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {

    public static Date now() {
        return Calendar.getInstance().getTime();
    }
    
    // Returns a Date for x days in the future
    public static Date then(int days){
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE, days);
    	return c.getTime();
    }
    
    // Returns the time till midnight
    // Used for interest, can be changed to whatever time the bank wishes to apply interest
    public static long tillMidnight(){
	    Calendar c = Calendar.getInstance();
	    c.add(Calendar.DAY_OF_MONTH, 1);
	    c.set(Calendar.HOUR_OF_DAY, 0);
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    c.set(Calendar.MILLISECOND, 0);
	    return (c.getTimeInMillis()-System.currentTimeMillis());
    }
}
