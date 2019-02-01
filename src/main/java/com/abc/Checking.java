package com.abc;

/**
 * This class represents Checking accounts, and could be modified to implement other features this account may need.
 * @author Daniel Seymour
 * @version 1.0
 */
public class Checking extends Account{
	
	/**
	 * Constructs an Account object corresponding to the field in the Account superclass.
	 */
	public Checking() {
		this.accountType = CHECKING;
	}
	
	/**
	 * Overrides "account" classes method and applies the appropriate
	 * interest rate of 0.1%.
	 * 
	 * 
	 * @return double representing the interest earned at the point in time its called.
	 */
	@Override
	public double interestEarned() {
		double amount = super.interestEarned();
		return amount * 0.001;
	}

}
