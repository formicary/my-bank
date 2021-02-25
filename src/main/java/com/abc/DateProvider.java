package com.abc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

public class DateProvider {

	private static DateProvider instance = null;

	public static DateProvider getInstance() {
		if (instance == null)
			instance = new DateProvider();
		return instance;
	}

	public LocalDate now() {
		return Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

}
