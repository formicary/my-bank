package com.abc;

import java.time.LocalDate;

public class DateProvider {

    // Clock package-private for testing
    LocalDate date;

    // Sets date and dateTime variables to present time when instantiated.
    public DateProvider(){
        date = LocalDate.now();
    }

    LocalDate getDate() {
        return date;
    }


    public boolean isOlderThan(LocalDate a, long days){
        return a.isBefore(date.minusDays(days));
    }

    public static boolean isSameDate(LocalDate date1, LocalDate date2){
        return date1.isEqual(date2);
    }

    // For testing the date.
    void setDate(LocalDate newDate){
        date = newDate;
    }

}

