package com.abc.bank.utils;

import java.util.Calendar;
import java.util.Date;

public enum DateProvider {
	INSTANCE;
	
	private static float MILLIS_IN_DAY = 60*60*24*1000F;
	
	/**
	 * Today 
	 *
	 * @return Normalized Date
	 */
	public Date today(){
		Calendar cal = Calendar.getInstance(); 
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	}
	
	//Too simple put good enough of dates generated using the above util.
	public int getDaysDiff(Date d0,Date d1){
		  return (int)Math.round((d0.getTime()-d1.getTime())/MILLIS_IN_DAY);
	}
}
