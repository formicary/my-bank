package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
	/* ten days expressed in milliseconds - for use in madeRecentWithdrawal */
	private static long oneDay = 1000 * 60 * 60 * 24;

	/**
	 * @return the current time
	 */
	public static Date now() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 
	 * @param d
	 *            a Date object
	 * @param num
	 *            the number of full days between the desired Date and the input
	 *            Date
	 * @return the Date object that was exactly num days before d
	 */
	public static Date daysBefore(Date d, int num) {
		long time = num * oneDay;
		Date period = new Date(d.getTime() - time);
		return period;
	}
}
