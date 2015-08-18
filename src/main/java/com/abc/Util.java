package main.java.com.abc;

import static java.lang.Math.abs;

public class Util {
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    public static double GetDailyFromAnnualRate(double annual){
    	return Math.pow((1 + annual/365),365) - 1;
    }
}
