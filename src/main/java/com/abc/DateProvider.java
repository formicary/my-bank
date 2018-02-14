package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * A class to provide functionality for a Date object.
 * 
 * @author JT
 *
 */
public class DateProvider {
	private static DateProvider instance = null;

	/**
	 * A method to generate an instance of a date provider.
	 * 
	 * @return An instance of a DateProvider object.
	 */
	public static DateProvider getInstance() {
		if (instance == null)
			instance = new DateProvider();
		return instance;
	}

	/**
	 * A method to set the time to the time when the method is invoked.
	 * 
	 * @return A date object which has time corresponding to the time when the
	 *         method was called.
	 */
	public Date now() {
		return Calendar.getInstance().getTime();
	}
}
