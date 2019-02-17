/**
 * 
 */
package com.abc;

/**
 * @author Harry
 *
 */
public class SavingsAccount extends Account {

	/**
	 * @param accountType
	 * @param openingBalance
	 */
	public SavingsAccount(double openingBalance) {
		super(SAVINGS, openingBalance);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param accountType
	 */
	public SavingsAccount() {
		super(SAVINGS, 0.0f);
		// TODO Auto-generated constructor stub
	}


	@Override
	public double interestEarned() {	
		double amount = this.getBalance();
		return (amount <= 1000) ? amount * 0.001 : 1 + (amount-1000) * 0.002;
	}
}

