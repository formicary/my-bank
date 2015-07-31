package com.abc;

public class MaxiSavingsAccount extends Account {

	private static final double NO_WITHDRAWL_IN_10DAYS_INTEREST = 0.05;
	private static final double WITHDRAWL_IN_10DAYS_INTEREST = 0.001;

	public MaxiSavingsAccount() {
		super(MAXI_SAVINGS);
	}

	@Override
	public double interestEarned() {
		double amount = sumTransactions();

		if (hasWithdrawnInLast10Days()) {
			return amount * WITHDRAWL_IN_10DAYS_INTEREST;
		} else {
			return amount * NO_WITHDRAWL_IN_10DAYS_INTEREST;
		}
	}

}
