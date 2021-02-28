package com.abc;

public class SavingsAccount extends Account {
	
	private final static String accountType = "SAVINGS";

	public SavingsAccount() {
		super(accountType);
	}

	@Override
	public double interestEarned() {
		double amount = this.sumTransactions();
		return amount <= 1000 ? amount * 0.001 : 1 + (amount - 1000) * 0.002;
	}

}
