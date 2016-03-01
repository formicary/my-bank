package com.abc;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * This is the maxi saving account.
 * 
 * @author Fei
 * 
 */
public class MaxiSavingAccount extends Account {
	// Lower interest rate if there are withdrawals in the past 10 days.
	private double lowInterestRate = 0.001;
	// High interest rate if there are no withdrawals in the past 10 days.
	private double highInterestRate = 0.05;

	public MaxiSavingAccount() {
		super();
		super.setAccountName("Maxi Saving Account");
	}

	/*
	 * Interest rate is 5% if there is no withdrawals in the past 10 days. 
	 * Otherwise it is 0.1%
	 * Calculated with compound daily approach.
	 * 
	 */
	@Override
	public double interestEarned() {
		// Interest rate
		double interest = 0;
		// Interest rate earned
		double interestEarn = 0;
		// The temporary balance after each transaction
		double tempBalance = 0;
		// The balance+interest after each transaction
		double periodTotal = 0;
		// Boolean variable to indicate if there are transaction in past 10 days
		boolean pastTenDaysTran = false;
		List<Transaction> transactions = getTransactions();
		// Check if there are withdraw transactions in the past 10 days;
		for (int i = transactions.size() - 1; i >= 0; i--) {
			Date transactionTime = transactions.get(i).getDate();
			Date now = DateProvider.getInstance().now();
			if (transactions.get(i).getAmount() < 0
					&& DateProvider.getInstance()
							.diffDays(transactionTime, now) <= 10) {
				pastTenDaysTran = true;
				break;
			}
			// Break after checking past 10 days' transactions
			if (DateProvider.getInstance().diffDays(transactionTime, now) > 10) {
				break;
			}
		}
		// Decide which interest rate to use
		if (pastTenDaysTran) {
			interest = lowInterestRate;
		} else {
			interest = highInterestRate;
		}

		// Calculate interest earned between each transaction
		for (int i = 0; i < transactions.size() - 1; i++) {
			tempBalance += transactions.get(i).getAmount();
			Date from = transactions.get(i).getDate();
			Date to = transactions.get(i + 1).getDate();
			periodTotal = tempBalance
					* Math.pow(1 + interest / 365, DateProvider.getInstance()
							.diffDays(from, to));
			interestEarn += (periodTotal - tempBalance);
		}
		// Calculate interest rate since the last transaction until now;
		Date now = DateProvider.getInstance().now();
		Date lastTransactionDate = transactions.get(transactions.size() - 1)
				.getDate();
		tempBalance += transactions.get(transactions.size() - 1).getAmount();
		periodTotal = tempBalance
				* Math.pow(1 + interest / 365, DateProvider.getInstance()
						.diffDays(lastTransactionDate, now));
		interestEarn += (periodTotal - tempBalance);
		
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
		return Double.valueOf(twoDecimals.format(interestEarn));
	}

	public double getLowInterestRate() {
		return lowInterestRate;
	}

	public void setLowInterestRate(double lowInterestRate) {
		this.lowInterestRate = lowInterestRate;
	}

	public double getHighInterestRate() {
		return highInterestRate;
	}

	public void setHighInterestRate(double highInterestRate) {
		this.highInterestRate = highInterestRate;
	}

}
