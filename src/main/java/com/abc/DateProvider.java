package com.abc;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateProvider {

    // Clock package-private for testing
    LocalDate date;
    LocalDateTime dateTime;

    // Sets date and dateTime variables to present time when instantiated.
    public DateProvider(){
        date = LocalDate.now();
        dateTime = LocalDateTime.now();
    }

    LocalDate getDate() {
        return date;
    }

    LocalDateTime getDateTime(){
        return dateTime;
    }

    public boolean isOlderThan(LocalDateTime a, long days){
        return a.isBefore(dateTime.minusDays(days));
    }

    public boolean isNewerThan(LocalDateTime a, long days){
        return a.isAfter(dateTime.minusDays(days));
    }

    public static boolean isSameDate(LocalDate date1, LocalDate date2){
        return date1.isEqual(date2);
    }

    // For testing the date.
    void setDate(LocalDate newDate){
        date = newDate;
    }

    void setDateTime(LocalDateTime newDateTime){
        dateTime = newDateTime;
    }
}

