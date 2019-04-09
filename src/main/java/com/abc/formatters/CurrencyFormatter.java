package com.abc.formatters;

import static java.lang.Math.abs;

/**
 * Provides string formatter for Currency values.
 */
public class CurrencyFormatter {

    /**
     * Singleton instance.
     */
    public static CurrencyFormatter INSTANCE = new CurrencyFormatter();

    private CurrencyFormatter() {
    }

    /**
     * Prints the amount as Dollar in the following format: $123.12.
     *
     * @param amount the amount to print.
     * @return the formatted representation of amount.
     */
    public String toDollars(final double amount) {
        return String.format("$%,.2f", abs(amount));
    }

}
