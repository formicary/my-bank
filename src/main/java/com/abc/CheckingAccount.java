package com.abc;

public class CheckingAccount extends Account {

	public CheckingAccount() {
		super();
	}
	
	/**
	 * Calculates the interest earned for a Checking Account
	 * @return interest earned
	 */
	@Override
    public double interestEarned() {
        double amount = sumTransactions();
        return amount * 0.001;
    }
	

}
