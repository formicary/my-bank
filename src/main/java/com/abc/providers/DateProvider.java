package com.abc.providers;

import java.util.Calendar;
import java.util.Date;

public enum DateProvider {
	
	INSTANCE;

    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    public static DateProvider getInstance(){
    	return INSTANCE;
    }
}
