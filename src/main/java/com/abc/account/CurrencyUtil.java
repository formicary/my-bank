package com.abc.account;

import static java.lang.Math.abs;

public final class CurrencyUtil {

	public static String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}
}
