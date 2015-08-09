package com.abc.providers;

import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class DateProviderImpl implements DateProvider{

	public Calendar getDate() {
		return Calendar.getInstance();
	}

}
