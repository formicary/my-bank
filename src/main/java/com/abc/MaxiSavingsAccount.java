package com.abc;

import java.time.LocalDate;

/**
 * A Checking Account.
 * <p>
 * This account pays interest at the rate of 5% assuming no
 * withdrawals in the past 10 days otherwise 0.1%
 */
public class MaxiSavingsAccount extends Account {
	private static final int FIVE_PERCENT_AFTER_DAYS_COUNT = 10;

	public MaxiSavingsAccount(String accountNumber) {
		super(accountNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	public double interestEarned() {
		double amount = sumTransactions();
		final boolean withdrawlWith10Days = noWithdrawalsWithDays(FIVE_PERCENT_AFTER_DAYS_COUNT);
		double interest = 0.05;
		if (withdrawlWith10Days) {
			interest = 0.001;
		}
		return amount * interest;
	}

	private boolean noWithdrawalsWithDays(int days) {
		final LocalDate now = getCurrentTimeProvider().now();
		final LocalDate dateBeforeDays = now.minusDays(days + 1);
		boolean withdrawlWIthinDays = false;
		for (Transaction transaction : getTransactions()) {
			if (transaction.getTime().isAfter(dateBeforeDays)) {
				withdrawlWIthinDays = true;
			}
		}
		return withdrawlWIthinDays;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAccountName() {
		return "Maxi Savings Account";
	}
}
