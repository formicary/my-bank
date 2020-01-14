package com.abc.Account;

public class SavingsAccount extends Account {

	public double earnedInterest() {
		double amount = getBalance();

		if (amount <= 1000) {
			return amount * 0.001;
		} else {
			return 1 + (amount - 1000) * 0.002;
		}

	}

	public String getAccountType() {
		return "Savings Account\n";
	}
}
