package com.abc;

public class CheckingAccount extends Account {

	public CheckingAccount() {
		super(CHECKING);
	}

	@Override
	public double interestEarned() {
		double amount = sumTransactions();
		return amount * 0.001;
	}

}
