package com.abc;

public class Savings extends Account{

	private static final double BOTTOM_RATE = 0.001;
	private static final int INTEREST_CAP = 1000;
	private static final double MAX_RATE = 0.002;
	
	public Savings() {
		super(accountTypes.Savings);
	}

	@Override
	public double interestEarned() {
		double amount = this.getBalance();
		if(amount < INTEREST_CAP) {
			return amount * BOTTOM_RATE;
		} else {
			return ((amount - INTEREST_CAP) * MAX_RATE)+(INTEREST_CAP*BOTTOM_RATE);
		}
	}

}
