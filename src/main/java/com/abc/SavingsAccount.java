package com.abc;

public class SavingsAccount extends Account {
	
	private static final AccountType TYPE = AccountType.SAVINGS;
	private static final double INTEREST_RATE = 0.001 / DAYS;
	private static final double PRO_INTEREST_RATE = 0.002 / DAYS;
	
	public SavingsAccount() {
		super();
	}

	public double interestEarned() {
		double balance = this.getBalance();
		double interestRate = balance > 1000 ? PRO_INTEREST_RATE : INTEREST_RATE;
		double interestEarned = balance * interestRate;
		increaseTotalInterestEarned(interestEarned);
		return interestEarned;
	}
	
	public AccountType getType() {
		return TYPE;
	}


}
