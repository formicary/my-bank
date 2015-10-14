package main.java.com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * helper class for Calendar Singleton pattern
 * 
 * @author Stavros Mobile
 * 
 */
public class DateProvider {

	/**
	 * instance of class
	 */
	private static DateProvider instance = null;

	/**
	 * Constructor - initialiser
	 * 
	 * @return instance
	 */
	public static DateProvider getInstance() {
		if (instance == null)
			instance = new DateProvider();
		return instance;
	}

	/**
	 * 
	 * @return full date
	 */
	public Date now() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 
	 * @return integer day of year
	 */
	public int getDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
	}
	
}
