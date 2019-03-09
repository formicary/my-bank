package com.abc;

import java.util.Calendar;

public interface IDateProvider {

	//Return the current date from the system
	Calendar getcurrentdate();

	//Get the number of days between two dates
	double daydifference(Calendar date1, Calendar date2);

	//Method to compare if one date is after the other
	boolean ifdateafter(Calendar date1, Calendar date2);

}