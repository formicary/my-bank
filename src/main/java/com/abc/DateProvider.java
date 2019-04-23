package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author fearg
 *
 */
public class DateProvider {

	private static DateProvider instance = null;

	/**
	 * 
	 * @return
	 */
    public static DateProvider getInstance() {
        if (instance == null) {
            instance = new DateProvider();
        }
        return instance;
    }

    /**
     * 
     * @return
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
}
