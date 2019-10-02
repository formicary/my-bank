package com.abc;

public class Checking extends Account{
	private static final double FLAT_RATE = 0.001;
	
	public Checking() {
		super(accountTypes.Checking);
	}

	@Override
	public double interestEarned() {
		return this.getBalance() * FLAT_RATE;
	}
	

}
