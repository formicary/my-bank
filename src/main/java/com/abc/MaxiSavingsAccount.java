package com.abc;

public class MaxiSavingsAccount extends Account{
	private static final double BASE_INTEREST_RATE = 0.001;
	private static final double BONUS_INTEREST_RATE = 0.05;
	
	/**
	 * Constructor for Maxi-Savings account
	 */
	
	public MaxiSavingsAccount() {
		super();
	}
	
	
	/**
	 * Method to calculate the interest for Maxi-Savings account. Interest is determined by transaction history.
	 * 
	 * @return interest
	 * 			double: value calculated for interest
	 */
	public double interestEarned() {
		double amount = sumTransactions();
		
		if(HasWithdrawnPast10days())
			return amount * BASE_INTEREST_RATE;
		else
			return amount * BONUS_INTEREST_RATE;
	}
}
