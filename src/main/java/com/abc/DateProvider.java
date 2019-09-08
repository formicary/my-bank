package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {

	public static Date now() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * Generates a date in the past
	 * 
	 * @param days
	 *            specified by the number days counting from today
	 * @return date past date
	 */
	public static Date giveDayInThePast(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -days);
		return cal.getTime();
	}
}
