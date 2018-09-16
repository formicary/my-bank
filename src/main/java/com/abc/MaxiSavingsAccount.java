package com.abc;

/**
 * A Checking Account.
 * <p>
 * This account pays interest at the rate of 5% assuming no
 * withdrawals in the past 10 days otherwise 0.1%
 */
public class MaxiSavingsAccount extends Account {
	public MaxiSavingsAccount(String accountNumber) {
		super(accountNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	public double interestEarned() {
		double amount = sumTransactions();
		if (amount <= 1000) {
			return amount * 0.02;
		}
		if (amount <= 2000) {
			return 20 + (amount - 1000) * 0.05;
		}
		return 70 + (amount - 2000) * 0.1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAccountName() {
		return "Maxi Savings Account";
	}
}
