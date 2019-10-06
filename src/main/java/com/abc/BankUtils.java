package com.abc;

public class BankUtils {
    static String toDollars(double amount) {
        return String.format("$%,.2f", amount);
    }
    static String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
}
