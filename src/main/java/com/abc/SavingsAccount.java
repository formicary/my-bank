package com.abc;

public class SavingsAccount extends Account {

	private final String ACCOUNT_NAME = "Savings Account";

	@Override
	public double interestEarned() {
		double amount = sumTransactions();
		double threshhold = 1000;

		if (amount <= threshhold)
			return amount * 0.001;
		else
			return 1 + (amount - threshhold) * 0.002;

	}

	@Override
	public String getAccountName() {
		return ACCOUNT_NAME;
	}

}
