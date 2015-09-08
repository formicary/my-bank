package com.abc;

import java.util.Date;
import java.util.Calendar;

public class AccountMaxiSavings extends Account {

	@Override
	public String getPrettyName() {
		return "Maxi Savings Account";
	}

	@Override
	public double interestEarned() {
		return interestEarned(DateProvider.INSTANCE.now());
	}

	public double interestEarned(Date onDate) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(onDate);
	    cal.add(Calendar.DATE, -10);
	    //TODO: Clarify spec - should this be calculated from midnight?
	    Date startDate = cal.getTime();
	    double amount = sumTransactionsBefore(onDate);
	    if (withdrawalsBetween(startDate, onDate)) {
	        return amount * 0.001;
	    } else {
	        return amount * 0.05;
	    }
	}

	private boolean withdrawalsBetween(Date start, Date end) {
	    for (Transaction t: getTransactions()){
	        if (t.getTransactionDate().after(start)
	            && !t.getTransactionDate().after(end)
	            && t.amount < 0) {
	                return true;
	            }
	    }
	    return false;
	}
}
