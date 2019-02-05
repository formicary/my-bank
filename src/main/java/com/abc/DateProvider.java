package main.java.com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
	
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    public static boolean moreThanTenDaysSince(Date currentTime, Date transactionTime){
    	return ((currentTime.getTime() - transactionTime.getTime()) / (24*60*60*1000)) > 10;
    }
}
