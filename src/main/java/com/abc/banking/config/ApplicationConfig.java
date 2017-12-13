package com.abc.banking.config;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class ApplicationConfig {
	public static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final int MONETARY_DECIMAL_PLACES_ALLOWED = 2;
	public static final RoundingMode MONETARY_ROUNDING_MODE = RoundingMode.HALF_UP;

	private ApplicationConfig() {}

}
