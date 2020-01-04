package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

	// Return the current date.
	public Date now() {
		return Calendar.getInstance().getTime();
	}

	// Return the date resulted from an addition of a specified number of days (as a
	// parameter) to the current date.
	public Date changeDay(int days) {
		Date today = now();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

}
