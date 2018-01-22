package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime(); 
    }
    
    // returns the difference in days from specified date till now
    public static long daysTillNow(Date from) { 
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	LocalDate dateBefore = LocalDate.parse(df.format(from), formatter);
    	return ChronoUnit.DAYS.between( dateBefore , LocalDate.now());    	
    }
}