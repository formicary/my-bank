package com.abc.account;

import com.abc.DateProvider;

public final class SavingsAccount extends Account {

	public SavingsAccount(DateProvider dp) {
		super(dp);
	}

	@Override
	public double interestEarned() {
		double amount = sumTransactions();

		if (amount <= 1000)
			return amount * 0.001;
		else
			return 1 + (amount - 1000) * 0.002;

	}

	@Override
	public String getStatement() {
		return "Savings Account\n" + getTransactionSummary();
	}
}
