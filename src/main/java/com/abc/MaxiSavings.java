package com.abc;

public class MaxiSavings extends Account {
	private static final double BOTTOM_RATE = 0.02;
	private static final int INTEREST_CAP = 1000;
	private static final double MID_RATE = 0.05;
	private static final double MAX_RATE = 0.1;
	
	public MaxiSavings() {
		super(accountTypes.Maxi_Savings);
	}

	@Override
	public double interestEarned() {
		double amount = this.getBalance();
		if(amount<INTEREST_CAP) {
			return amount * BOTTOM_RATE;
		} else if(amount < (INTEREST_CAP*2)){
			return ((amount - INTEREST_CAP) * MID_RATE)+(INTEREST_CAP*BOTTOM_RATE);
		} else {
			return ((amount - INTEREST_CAP*2) * MAX_RATE)+(INTEREST_CAP*BOTTOM_RATE)+(INTEREST_CAP*MID_RATE);
		}
	}

}
