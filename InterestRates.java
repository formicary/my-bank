package com.abc;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

public class InterestRates {
	private final int daysInYear = 365;
	private DateProvider dateProvider;

	/* class used for calculating interests.
	 * 
	 */
	
	public InterestRates() {
		dateProvider = new DateProvider();
	}

	public double checkingInterestRate(double amount) {
		return amount * (0.001 / daysInYear);
	}

	public double savingsInterestRate(double amount) {
		if (amount <= 1000)
			return amount * (0.001 / daysInYear);
		else
			return 1 + (amount - 1000) * (0.002 / daysInYear);
	}

	public double maxiSavingsInterestRate(double amount, List<Transaction> transactions) {
		for (int i = 0; i < transactions.size(); i++) {
			if (transactions.get(i).getTransactionDate().after(dateProvider.tenDays())
					&& transactions.get(i).amount < 0) {
				return amount * (0.001 / daysInYear);
			}
		}

		return amount * (0.05 / daysInYear);
	}
	
	public double accruedInterest(double amount) {
		return amount ;
	}
}
