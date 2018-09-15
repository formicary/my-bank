package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Provides the current {@link Date}.
 */
public class DateProvider {
	private static DateProvider instance = null;

	/**
	 * Returns a single instance of the {@link DateProvider}.
	 * 
	 * @return See above.
	 */
	public static DateProvider getInstance() {
		if (instance == null)
			instance = new DateProvider();
		return instance;
	}

	/**
	 * Returns the current date/time.
	 * 
	 * @return See above.
	 */
	public Date now() {
		return Calendar.getInstance().getTime();
	}
}
