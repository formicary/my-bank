package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	
	 public static Date getDateBeforeDays(int days) {
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, days);
	    return cal.getTime();
	 }
	    
	 public static int getDayCount(Date fromDate, Date toDate) {
	    long difference = fromDate.getTime() - toDate.getTime();
	    return Math.abs((int)(difference / (1000*60*60*24)));
	 }

}
