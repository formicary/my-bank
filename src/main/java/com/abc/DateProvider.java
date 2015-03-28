package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Wrapper class to get current time of <code>Date</code> instance.
 */

public enum DateProvider {
	/**
	 * The only instance of this class. It should be used to call <code>now()</code> method.
	 */
    INSTANCE;     
    
    /**
     * @return date, current time.
     */
    public Date now() {	
        return Calendar.getInstance().getTime();
    }
}
