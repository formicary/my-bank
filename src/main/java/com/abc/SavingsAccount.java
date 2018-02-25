package com.abc;

public class SavingsAccount extends Account{
	
	private static final double BASE_INTEREST_RATE = 0.001;
	private static final double HIGHER_INTEREST_RATE = 0.002;
	
	/**
	 * Constructor for Savings Account
	 */
	
	public SavingsAccount() {
		super();
	}
	
	
	/**
	 * Method to calculate the interest for Savings account. Interest is determined by balance.
	 * 
	 * @return interest
	 * 			double: value calculated for interest
	 */
	public double interestEarned() {
	        double amount = sumTransactions();
	
	        if (amount <= 1000)
	            return amount * BASE_INTEREST_RATE;
	        else
	            return 1 + (amount-1000) * HIGHER_INTEREST_RATE;
	    }
}


