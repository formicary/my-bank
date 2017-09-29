package com.abc;

import java.time.LocalDateTime;

public class DateProvider {

    private static DateProvider instance = null;
    private static Object mutex = new Object();
    private DateProvider() {

    }

    public static DateProvider getInstance() {
        if(instance == null){
            synchronized (mutex){
                if(instance == null) instance = new DateProvider();
            }
        }
        return instance;
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
