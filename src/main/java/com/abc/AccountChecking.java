package com.abc;

public class AccountChecking extends Account {

	@Override
	public String getPrettyName() {
		return "Checking Account";
	}

	@Override
	public double interestEarned() {
		double amount = sumTransactions();
		return amount * 0.001;
	}

}
