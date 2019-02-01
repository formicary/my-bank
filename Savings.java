package com.abc;

/**
 * This class represents Savings accounts, and could be modified to implement other features this account may need.
 * @author Daniel Seymour
 * @version 1.0
 */
public class Savings extends Account{

	/**
	 * Constructs an Account object corresponding to the field in the Account superclass.
	 */
	public Savings() {
		this.accountType = SAVINGS;
	}
	
	/**
	 * Overrides the "Account" classes method and applies the appropriate interest
	 * rates for a Savings Account.
	 */
	@Override
	public double interestEarned() {
		double amount = super.interestEarned();
		if (amount <= 1000) {
			return amount * 0.001;
		}
		else {
			return 1 + (amount - 1000) * 0.002;
		}
	}
}
