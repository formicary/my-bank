package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * DateProvider class moves date forward 
 * @version 2.0 03 July 2019 * 
 * @updated by Dhurjati Dasgupta
 */

public class DateProvider {
	private static DateProvider instance = null;

	public static DateProvider getInstance() {
		if (instance == null)
			instance = new DateProvider();
		return instance;
	}

	private static int moveDateForward = 0;
	
	/* sets number of days the date to be moved forward */

	public static void setDateForward(int numberOfDays) {
		moveDateForward = numberOfDays;

	}
/*Returns Date based on number days date moved forward */
	public Date now() {
/*date moves forward by number of days set in  moveDateForward variable*/
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, moveDateForward);
		dt = c.getTime();
		return dt;
	}
}
