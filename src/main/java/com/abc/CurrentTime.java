package com.abc;

import java.util.Calendar;
import java.util.Date;

public class CurrentTime implements Clock{

	public Date now() {
		return Calendar.getInstance().getTime();
	}

}
