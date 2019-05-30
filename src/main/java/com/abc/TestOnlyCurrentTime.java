package com.abc;

import java.util.Date;

public class TestOnlyCurrentTime implements Clock{
	private static Date date;
	private static TestOnlyCurrentTime instance = null;

    public static TestOnlyCurrentTime getInstance() {
        if (instance == null)
            instance = new TestOnlyCurrentTime();
        return instance;
    }

	public Date now() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

}
