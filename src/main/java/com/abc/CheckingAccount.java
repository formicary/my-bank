package com.abc;

import java.util.concurrent.TimeUnit;

public class CheckingAccount extends Account {

	// Account type.
	private static final String CHECKING = "CHECKING";
	// Stable rate for this account type.
	private static final double INTEREST_RATE = 0.001;

	public CheckingAccount() {
		super();
		this.accountType = CHECKING;
		totalInterestAmount = 0.0;
		lastUpdate = getOpeningDate();
		tempInterest = 0.0;
	}

	@Override
	// Update the current interest earned according to the current transaction.
	public void update(long time, double amount) {
		// Calculate the number of days from the last update and convert it to days.
		long diff = time - lastUpdate;
		long days = TimeUnit.DAYS.convert((long) diff, TimeUnit.MILLISECONDS);

		// For transactions in the same day the temporary interest gets reseted and
		// re-added. At the end of the day the last interest added is final.
		// The interest for multiple days is calculated as the fixed interest rate
		// multiplied by the number of days -1 (since the interest from the first day is
		// already added) multiplied by the balance before the current transaction takes
		// effect.
		if (days > 0) {
			totalInterestAmount += INTEREST_RATE * (days - 1) * getBalance();
		} else {
			totalInterestAmount -= tempInterest;
		}
		tempInterest = INTEREST_RATE * (getBalance() + amount);
		totalInterestAmount += tempInterest;
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
		return CHECKING;
	}

}