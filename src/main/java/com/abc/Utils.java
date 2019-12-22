package com.abc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.abs;

public class Utils {

    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    public static String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    //Needed for testing
    public static final double DOUBLE_DELTA = 1e-4;

    //Convert string to Date
    public static Date getDate(String sDate){
        Date d = null;
        try {
            d = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }
}
