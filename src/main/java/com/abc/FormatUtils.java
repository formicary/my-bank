package com.abc;

public class FormatUtils {
    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    public static String toPlural(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public static String toDollars(double d) {
        return String.format("$%,.2f", d);
    }
}
