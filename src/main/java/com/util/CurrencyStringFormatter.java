package com.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyStringFormatter {
    public static String format(BigDecimal amount) {
        Locale locale = getLocale();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(getLocale());
        
        return currencyFormatter.format(amount);
    }

    private static Locale getLocale() {
        return new Locale("en", "US"); // default
    }
}
