package com.abc;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateProvider {
	
	public static LocalDateTime now() {
        return LocalDateTime.now();
    }
	
	public static String formattedDate(LocalDateTime date) {
		return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
