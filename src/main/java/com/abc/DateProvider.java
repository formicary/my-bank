package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {

	private DateProvider() {
		throw new IllegalStateException("Utility class");
	}

	public static Date now() {
		return Calendar.getInstance().getTime();
	}

	public static Date tenDaysPast() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -10);
		return cal.getTime();
	}

	public static Date addDays(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}
}
