package com.abc;

import static java.lang.Math.abs;

import java.util.Date;
import java.util.concurrent.TimeUnit;

class HelperClass {
	
	public static long calculateDateDifference(Date fromDate, Date toDate) {
		return TimeUnit.DAYS.convert(Math.abs(fromDate.getTime() - toDate.getTime()), TimeUnit.MILLISECONDS);
	}
	
	public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
