package com.abc;

import static java.lang.Math.abs;

import java.text.NumberFormat;
import java.util.Locale;

public class Common {
	/**
	 * Display amount in Canadian currency
	 * @param d amount to convert
	 * @return amount in Canadian currency
	 */
    public static String toDollars(double d) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CANADA);
		String currency = nf.format(abs(d));
		return currency;
	}

}
