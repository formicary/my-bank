package com.abc;

import java.text.DecimalFormat;
import java.util.Collections;

public class ReportFormatter {

    public static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");

    // Returns a line of the specified symbol which is numChars characters long
    public static String makeSymbolLine(char symbol, int numChars) {
        return String.join("", Collections.nCopies(numChars, Character.toString(symbol))) + "\n";
    }
}
