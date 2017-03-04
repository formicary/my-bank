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
            return amount * 0.02;
        }
        else if (amount <= 2000) {
            return 20 + (amount-1000) * 0.05;
        }
        return 70 + (amount-2000) * 0.1;          
    }

}
