package com.abc;

/**
 * This class represents Maxi-Savings accounts, and could be modified to implement other features this account may need.
 * @author Daniel Seymour
 * @version 1.0
 */
public class MaxiSavings extends Account{

	/**
	 * Constructs an Account object corresponding to the field in the Account superclass.
	 */
	public MaxiSavings() {
		this.accountType = MAXI_SAVINGS;
	}
	
	/**
	 * Overrides the "Account" classes method and applies the appropriate interest
	 * rates for a Maxi-Savings Account.
	 */
	@Override
	public double interestEarned() {
		double amount = super.interestEarned();
		if (amount <= 1000) {
			return amount * 0.02;
		}
		if (amount <= 2000) {
			return 20 + (amount - 1000) * 0.05;
		}
		return 70 + (amount - 2000) * 0.1;
	}

}
