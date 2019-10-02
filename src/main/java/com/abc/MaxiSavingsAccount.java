package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MaxiSavingsAccount extends Account{

	@Override
	public String toString() {
		return "Maxi-Savings Account";
	}

	@Override
	public double getInterest() {
		Date currentDate = Calendar.getInstance().getTime();
		long diffMs = currentDate.getTime() - lastWithdrawalDate.getTime();
	    long diff = TimeUnit.DAYS.convert(diffMs, TimeUnit.MILLISECONDS);
		
	    return (diff >= 10) ? sumTransactions() * 0.05 : sumTransactions() * 0.001;
	}
}
