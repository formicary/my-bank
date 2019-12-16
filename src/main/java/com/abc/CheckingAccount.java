package com.abc;

public class CheckingAccount extends Account {
	
	private static final AccountType TYPE = AccountType.CHECKING;
	private static final double INTEREST_RATE = 0.001 / DAYS;
	
	public CheckingAccount() {
		super();
	}

	public double interestEarned() {
		double balance = getBalance();
		double interestEarned = balance * INTEREST_RATE;
		increaseTotalInterestEarned(interestEarned);
		return interestEarned;
	}

	public AccountType getType() {
		return TYPE;
	}

}
