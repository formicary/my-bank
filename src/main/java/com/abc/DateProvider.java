package com.abc;

// java.util.Calender a bit outdated, so using LocalDateTime instead
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
    
    /**
     * Calculates the difference in days between now and the given date
     * 
     * @param datetime 
     * @return absolute difference in days between now and given date.
     */
    public int differenceInDays(LocalDateTime datetime){
        return (int)Math.abs(ChronoUnit.DAYS.between(now().toLocalDate(), datetime.toLocalDate()));
    }

}
