package com.abc;

import java.math.BigDecimal;

class BankUtils {
    static String toDollars(BigDecimal amount) {
        return String.format("$%,.2f", amount);
    }
    static String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
}
