package com.abc;

public class CheckingAccount extends Account {

	private final String ACCOUNT_NAME = "Checking Account";

	@Override
	public double interestEarned() {
		return sumTransactions() * 0.001;
	}

	public String getAccountName() {
		return ACCOUNT_NAME;
	}

}
