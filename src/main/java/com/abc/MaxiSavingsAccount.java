package com.abc;

public class MaxiSavingsAccount extends SavingsAccount {

	public MaxiSavingsAccount() {
		super();
	}

	/**
	 * Calculates the interest earned for a Savings Account
	 * @return interest earned
	 */
	@Override
    public double interestEarned() {
        double amount = sumTransactions();
	    if (amount <= 1000) {
	    	return amount * 0.001;
	    }
	          
	    else {
	    	return 1 + (amount-1000) * 0.002;
	    }	          
    }

}
