package com.abc;

import java.util.Calendar;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();

        cal.setTime(currentDate);
        System.out.println(cal.getTime());
        //manipulate date
        cal.add(Calendar.DATE, -10); 

        System.out.println(3000 * (Math.pow(1 + (0.05/365), 365) - 1));
    }
}
