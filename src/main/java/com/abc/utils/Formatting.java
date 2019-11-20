package com.abc.utils;

/**
 * Utility class to aid with common formatting operations persistent throughout the application.
 */
public class Formatting {
    /**
     * Generates a string depicting the amount specified as a valid dollar amount.
     * @param amount the amount of money to represent in dollars
     * @return the appropriately formatted string.
     */
    public static String toDollars(double amount) {
        String prefix = (amount > 0) ? "" : "-";
        return prefix + String.format("$%,.2f", Math.abs(amount));
    }

    /**
     * Pluralises the number of accounts a customer has
     * @param number the number of accounts
     * @return the appropriate plurality with respect to the number of accounts present
     */
    public static String pluralise(int number) {
        return number + " " + (number == 1 ? "account" : "accounts");
    }
}
