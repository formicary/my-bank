package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Provide current date for transactions.
 * Calculate days between two date
 * 
 * @author others and Fei
 *
 */
public final class DateProvider {

	private static DateProvider instance = null;

	public static DateProvider getInstance() {
		if (instance == null)
			instance = new DateProvider();
		return instance;
	}

	/**
	 * @return Date Current time
	 */
	public Date now() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * Calculate the number of days between two date
	 * 
	 * @param from Date The start date 
	 * @param to Date The end date
	 * @return int The number of days between from and to
	 */
	public int diffDays(Date from, Date to) {
		long diff = to.getTime() - from.getTime();
		int days = (int) (diff / (1000 * 60 * 60 * 24));
		return days;
	}
}
