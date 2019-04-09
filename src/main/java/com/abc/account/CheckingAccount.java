package com.abc.account;

import com.abc.DateProvider;

public final class CheckingAccount extends Account {

	public CheckingAccount(DateProvider dp) {
		super(dp);
	}

	@Override
	public double interestEarned() {
		return sumTransactions() * 0.001;
	}

	@Override
	public String getStatement() {
		return "Checking Account\n" + getTransactionSummary();
	}
}
