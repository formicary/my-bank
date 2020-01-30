package com.abc;

public class Savings extends Account {
	
	public static final int SAVINGS = 1;
	
	public Savings() {
		super(SAVINGS);
	}
	
	@Override
	//How interest is calculated for SAVINGS accounts.
	public double interestEarned() {
		double amount = sumTransactions();
		if (amount <= 1000) {
			return amount * 0.001;
		} else {
			return 1 + (amount-1000) * 0.002;
		}
	}

}
