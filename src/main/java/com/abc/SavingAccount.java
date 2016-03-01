package com.abc;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Saving accounts with high and low interest rate above 
 * and below the threshold
 * 
 * @author Fei
 *
 */
public class SavingAccount extends Account {
	//Threshold for deciding interest rate
	private double interestThreshold;
	//Interest rate below threshold
	private double belowInterest;
	//Interest rate above threshold
	private double aboveInterest;

	public SavingAccount() {
		super();
		this.interestThreshold = 1000;
		this.belowInterest = 0.001;
		this.aboveInterest = 0.002;
		super.setAccountName("Saving Account");
	}

	/* 
	 * Interest rate is 0.1% for the first 1000 dollars.
	 * Above 1000 dollars with interest rate of 0.2%
	 * Calculated with compound daily approach.
	 */
	@Override
	public double interestEarned() {
		//Interest earned
		double interestEarn = 0;
		//Temporary balance after each transaction
		double tempBalance = 0;
		//Temporary balance+interest rate after each transaction
		double periodTotal = 0;
		List<Transaction> transactions = getTransactions();
		// Calculate compound daily interest between transactions
		for (int i = 0; i < transactions.size() - 1; i++) {
			tempBalance += transactions.get(i).getAmount();

			Date from = transactions.get(i).getDate();
			Date to = transactions.get(i + 1).getDate();
			// check if the balance above or below the threshold and calculate
			// interest accordingly
			if (tempBalance <= interestThreshold) {
				periodTotal = tempBalance
						* Math.pow(1 + belowInterest / 365, DateProvider
								.getInstance().diffDays(from, to));
			} else {
				periodTotal = interestThreshold
						* Math.pow(1 + belowInterest / 365, DateProvider
								.getInstance().diffDays(from, to))
						+ (tempBalance - interestThreshold)
						* Math.pow(1 + aboveInterest / 365, DateProvider
								.getInstance().diffDays(from, to));
			}
			interestEarn += (periodTotal - tempBalance);
		}
		// Calculate interest rate since the last transaction until now;
		tempBalance += transactions.get(transactions.size() - 1).getAmount();
		Date now = DateProvider.getInstance().now();
		Date lastTransactionDate = transactions.get(transactions.size() - 1)
				.getDate();
		if (tempBalance <= interestThreshold) {
			periodTotal = tempBalance
					* Math.pow(1 + belowInterest / 365, DateProvider
							.getInstance().diffDays(lastTransactionDate, now));
		} else {
			periodTotal = interestThreshold
					* Math.pow(1 + belowInterest / 365, DateProvider
							.getInstance().diffDays(lastTransactionDate, now))
					+ (tempBalance - interestThreshold)
					* Math.pow(1 + aboveInterest / 365, DateProvider
							.getInstance().diffDays(lastTransactionDate, now));
		}
		interestEarn += (periodTotal - tempBalance);
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
		return Double.valueOf(twoDecimals.format(interestEarn));
	}

	public double getInterestThreshold() {
		return interestThreshold;
	}

	public void setInterestThreshold(double interestThreshold) {
		this.interestThreshold = interestThreshold;
	}

	public double getBelowInterest() {
		return belowInterest;
	}

	public void setBelowInterest(double belowInterest) {
		this.belowInterest = belowInterest;
	}

	public double getAboveInterest() {
		return aboveInterest;
	}

	public void setAboveInterest(double aboveInterest) {
		this.aboveInterest = aboveInterest;
	}

}
