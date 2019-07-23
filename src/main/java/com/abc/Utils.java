package com.abc;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static java.lang.Math.abs;

public class Utils {

    public String toDollars(double d) {
        return toDecimalFormat(abs(d));
    }

    public String toDecimalFormat(double number) {
        Locale currentLocale = Locale.getDefault();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(currentLocale);
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');

        String pattern = "$###,##0.00";
        DecimalFormat formatter = new DecimalFormat(pattern, symbols);

        return formatter.format(number);
    }
}
