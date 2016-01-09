package com.abc;

import static java.lang.Math.abs;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    public static String format(int number, String word) {
        return number + " " + (number <= 1 ? word : word + "s");
    }

    
    public static int dateDifferenceinDays(Date dateAfter, Date dateBefore) {
        long difference = dateAfter.getTime() - dateBefore.getTime();
        int days = (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
        return days;
    }
    
}
