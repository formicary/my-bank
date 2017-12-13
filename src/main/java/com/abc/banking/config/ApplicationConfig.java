package com.abc.banking.config;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class ApplicationConfig {
	public static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
	public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final int monetaryDecimalPlacesAllowed = 2;
	public static final RoundingMode monetaryRoundingMode = RoundingMode.HALF_UP;

	private ApplicationConfig() {}

}
