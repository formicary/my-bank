package com.abc;

import static java.lang.Math.abs;

import java.text.NumberFormat;
import java.util.Locale;

public class Common {
	
	public static final double MONEY_ZERO = 0.00;
	public static final Integer DEPOSIT = 1;
	public static final Integer WITHDRAW = 2;
	public static final Integer TRANSFER_DEPOSIT = 3;
	public static final Integer TRANSFER_WITHDRAW = 4;
	public static final Integer TRANSFER = 0;
	public static final String DEPOSIT_STR = "deposit";
	public static final String WITHDRAW_STR = "withdrawal";
	public static final String TRANSFER_DEPOSIT_STR = "transfer in";
	public static final String TRANSFER_WITHDRAW_STR = "transfer out";	
	
	/**
	 * Display amount in Canadian currency
	 * @param d amount to convert
	 * @return amount in Canadian dollars
	 */
    public static String toDollars(double d) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CANADA);
		String currency = nf.format(abs(d));
		return currency;
	}

}
