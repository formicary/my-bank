package com.abc;

import java.util.concurrent.TimeUnit;

public class SavingsAccount extends Account {

	private static final String SAVINGS = "SAVINGS";
	private static final double INTEREST_RATE1 = 0.001;
	private static final double INTEREST_RATE2 = 0.002;
	//Threshold after witch the second rate gets applied.
	private static final double FIRST_THRESHOLD = 1000;

	public SavingsAccount() {
		super();
		this.accountType = SAVINGS;
		totalInterestAmount = 0.0;
		lastUpdate = getOpeningDate();
		tempInterest = 0.0;
	}

	@Override
	// Update the current interest earned according to the current transaction.
	public void update(long time, double amount) {
		long diff = time - lastUpdate;
		long days = TimeUnit.DAYS.convert((long) diff, TimeUnit.MILLISECONDS);
		// Helper variable that represent the balance after the transaction gets
		// processed.
		double balance = getBalance() + amount;
		// For transactions in the same day the temporary interest gets reseted and
		// re-added. At the end of the day the last interest added is final.
		// The interest for multiple days is calculated as the interest rate depending
		// on the current balance (before the transaction gets processed) multiplied by
		// the number of days -1 (since the interest from the first day is already
		// added) multiplied by the balance before the current transaction takes effect.
		// If the balance is under or equal to 1000 currency, only the base rate gets
		// applied, otherwise, apart from the first 1000 currency, a second rate gets
		// applied to the rest of the balance.
		if (days > 0) {
			if (balance <= 1000) {
				totalInterestAmount += INTEREST_RATE1 * (days - 1) * getBalance();
				tempInterest = INTEREST_RATE1 * (balance);
			} else {
				totalInterestAmount += (days - 1)
						* (INTEREST_RATE2 * (getBalance() - FIRST_THRESHOLD) + INTEREST_RATE1 * FIRST_THRESHOLD);
				tempInterest = INTEREST_RATE2 * (balance - FIRST_THRESHOLD) + INTEREST_RATE1 * FIRST_THRESHOLD;
			}
		} else {
			totalInterestAmount -= tempInterest;
			if (balance <= 1000) {// see if does the 2 indentations here,tabs at front and spaces between -
				tempInterest = INTEREST_RATE1 * (balance);
			} else {
				tempInterest = INTEREST_RATE2 * (balance - FIRST_THRESHOLD) + INTEREST_RATE1 * FIRST_THRESHOLD;
			}
		}
		totalInterestAmount += tempInterest;
		lastUpdate = time;
	}

	@Override
	// Return total interest earned
	public double getTotalInterestEarned() {
		return totalInterestAmount;
	}

	@Override
	// Return the account type.
	public String getAccountType() {
		return SAVINGS;
	}
}