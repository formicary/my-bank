package com.abc;

import java.util.Calendar;

public interface ITransaction {

	Calendar getTransactionDate();

	void setTransactionDate(int year, int month, int day, int hour, int min, int sec);

	double getTransactionAmount();

}