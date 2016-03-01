package com.abc;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Checking account with flat interest rate
 * 
 * @author Fei
 *
 */
public class CheckingAccount extends Account {
    // This is the basic account with flat interest rate
	private double annualInterestRate;

	public CheckingAccount() {
		super();
		super.setAccountName("Checking Account");
		this.annualInterestRate = 0.001;
	}

	public void setInterestRate(double i) {
		annualInterestRate = i;
	}

	public double getInterestRate() {
		return annualInterestRate;
	}

	/* 
	 * The interest rate is flat. 
	 * Calculated using compound daily approach.
	 * 
	 */
	@Override
	public double interestEarned() {
		//Interest earned
		double interestEarn = 0;
		//Temporary balance after each transaction
		double tempBalance = 0;
		//Temporary balance+interest after each transaction
		double periodTotal = 0;
		List<Transaction> transactions = getTransactions();
		// Calculate compound daily interest between transactions
		for (int i = 0; i < transactions.size() - 1; i++) {
			tempBalance += transactions.get(i).getAmount();
			Date from = transactions.get(i).getDate();
			Date to = transactions.get(i + 1).getDate();
			periodTotal = tempBalance
					* Math.pow(1 + annualInterestRate / 365, DateProvider
							.getInstance().diffDays(from, to));
			interestEarn += (periodTotal - tempBalance);
		}
		// Calculate interest rate since the last transaction until now;
		Date now = DateProvider.getInstance().now();
		Date lastTransactionDate = transactions.get(transactions.size() - 1)
				.getDate();
		tempBalance += transactions.get(transactions.size() - 1).getAmount();
		periodTotal = tempBalance
				* Math.pow(1 + annualInterestRate / 365, DateProvider
						.getInstance().diffDays(lastTransactionDate, now));
		interestEarn += (periodTotal - tempBalance);
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
		return Double.valueOf(twoDecimals.format(interestEarn));
	}

}
