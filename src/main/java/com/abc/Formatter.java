package com.abc;

import static java.lang.Math.abs;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

	public static String toDollars(double amount) {
		return String.format("$%,.2f", abs(amount));
	}

	public static String toTwoDecimal(double amount) {
		DecimalFormat df2 = new DecimalFormat("#.##");
		return df2.format(amount);
	}

	public static String toSimpleDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	public static String toPluralForm(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}
}
