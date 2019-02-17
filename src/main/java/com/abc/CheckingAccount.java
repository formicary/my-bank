/**
 * 
 */
package com.abc;

/**
 * @author Harry
 *
 */
public class CheckingAccount extends Account {

	/**
	 * @param accountType
	 * @param openingBalance
	 */
	public CheckingAccount(double openingBalance) {
		super(CHECKING, openingBalance);
	}

	/**
	 * @param accountType
	 */
	public CheckingAccount() {
		super(CHECKING, 0.0f);
	}

	@Override
	public double interestEarned() {
		return sumTransactions() * 0.001;	
	}

}
