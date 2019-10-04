package helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateProvider {
	private static DateProvider instance = null;
	Date date = new Date();
	LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	public static DateProvider getInstance() {
		if (instance == null)
			instance = new DateProvider();
		return instance;
	}

	public LocalDate now() {
		return LocalDate.now();
	}
	
}
