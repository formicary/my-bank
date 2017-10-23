package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * A singleton class for providing a date for the abc banking system.
 * 
 * @author Christopher J. Smith
 */
public class DateProvider {
	private static DateProvider instance = null;

	/**
	 * Get the singleton instance. This is synchronized so should avoid over calling
	 * and store a reference where possible.
	 * 
	 * @return Returns the singleton reference.
	 */
	// Synchronized to ensure that one call cannot overwrite another call when
	// creating an instance.
	public static synchronized DateProvider getInstance() {
		if (instance == null) {
			instance = new DateProvider();
		}
		return instance;
	}

	// Private constructor to ensure is singleton.
	private DateProvider() {
	}

	/**
	 * Get a date representing the current time.
	 * 
	 * @return Returns a date representing the current time.
	 */
	public Date now() {
		return Calendar.getInstance().getTime();
	}
}
