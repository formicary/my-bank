package com.abc;

import java.util.concurrent.TimeUnit;

public class MaxiSavingsAccount extends Account {

	// Account type.
	private static final String MAXI_SAVINGS = "MAXI SAVINGS";
	// Minimum number of days to achieve to get MAXI_RATE.
	private int minDays;
	// Global variable.
	private boolean withdraw;
	// Date of the last withdraw.
	private long lastWithdraw;
	private static final double INTEREST_RATE = 0.001;
	private static final double MAXI_RATE = 0.05;

	public MaxiSavingsAccount() {
		super();
		this.accountType = MAXI_SAVINGS;
		totalInterestAmount = 0.0;
		lastUpdate = getOpeningDate();
		tempInterest = 0.0;
		minDays = 10;
		this.withdraw = false;
		this.lastWithdraw = getOpeningDate();
	}

	@Override
	// Update the current interest earned according to the current transaction.
	public void update(long time, double amount) {

		// Detect a withdraw.
		if (amount < 0) {
			withdraw = true;
		}
		// Calculate the number of days from the last update and convert it to days.
		long diff = time - lastUpdate;
		long days = TimeUnit.DAYS.convert((long) diff, TimeUnit.MILLISECONDS);
		double interest = 0.0;
		// Calculate the number of days from the last withdraw and convert it to days.
		long lastWithdrawDay = TimeUnit.DAYS.convert((long) (time - lastWithdraw), TimeUnit.MILLISECONDS);
		// For transactions in the same day the temporary interest gets reseted and
		// re-added. At the end of the day the last interest added is final.
		// The interest for multiple days is calculated as the interest rate depending
		// on the last withdraw date multiplied by the number of days -1 (since the
		// interest from the first day is already added) multiplied by the balance
		// before the current transaction takes effect. If there are less then minDays
		// from the last withdraw then apply the base rate, otherwise apply the
		// Maxi_Rate.
		if (days > 0) {
			if (lastWithdrawDay > minDays) {
				interest += (lastWithdrawDay - minDays - 1) * MAXI_RATE;
				interest += (minDays - 1) * INTEREST_RATE;
			} else {
				interest += (lastWithdrawDay - 1) * INTEREST_RATE;
			}
			totalInterestAmount += interest * getBalance();
		} else {
			totalInterestAmount -= tempInterest;
		}

		// The MAXI_RATE gets applied on the current transaction only if it not a and
		// the number of days since the last withdraw is greater than the minDays
		// specified in the task.
		if (lastWithdrawDay > minDays && withdraw == false) {
			tempInterest = MAXI_RATE * (getBalance() + amount);
		} else {
			tempInterest = INTEREST_RATE * (getBalance() + amount);
		}
		totalInterestAmount += tempInterest;
		// Reset the last withdraw date.
		if (withdraw == true) {
			withdraw = false;
			lastWithdraw = time;
		}
		lastUpdate = time;
	}

	@Override
	// Return total interest earned.
	public double getTotalInterestEarned() {
		return totalInterestAmount;
	}

	@Override
	// Return the account type.
	public String getAccountType() {
		return MAXI_SAVINGS;
	}

}