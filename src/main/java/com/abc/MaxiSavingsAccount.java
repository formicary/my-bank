package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MaxiSavingsAccount extends Account {
	
	public MaxiSavingsAccount(int accountNumber) {
		super(accountNumber);
	}
	
	public double interestEarned() {
		return updateBalance() * getInterestRate();	
	}	
	
	public double setInterestRate() {
		if (!withdrawals.isEmpty()) {
			if ( HelperClass.calculateDateDifference(DateProvider.getInstance().now(), 
					withdrawals.get(withdrawals.size()-1).getTransactionDate()) < 10)
				return 0.001;
		}
		return 0.05;
	}
	
	@Override
	public String toString() {
		
		return "Maxi Savings Account\n" + super.toString();
	}

}
