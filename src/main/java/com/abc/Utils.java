package com.abc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    public static String toPlural(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public static String toDollars(double d) {
        return String.format("$%,.2f", d);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return true;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }
}
