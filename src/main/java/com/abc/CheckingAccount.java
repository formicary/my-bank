package com.abc;

public class CheckingAccount extends Account {

	private static final double CHECKING_INTEREST_RATE = 0.001;
	
	/**
	 * Constructor for CheckingAccount
	 * 
	 */
	public CheckingAccount() {
		super();
	}
	
	/**
	 *  Method to calculate the interest for the Checking Account
	 *  @return interest
	 *  		double: value calculated for interest
	 *  
	 */
	public double interestEarned() {
		return sumTransactions() * CHECKING_INTEREST_RATE; 
	}
}
