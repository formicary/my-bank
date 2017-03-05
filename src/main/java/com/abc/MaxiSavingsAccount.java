package com.abc;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MaxiSavingsAccount extends SavingsAccount {

	public MaxiSavingsAccount() {
		super();
	}

	/**
	 * Calculates the interest earned for a Maxi-Savings Account
	 * @return interest earned
	 */
	@Override
    public double interestEarned() {
        double amount = sumTransactions();
        if (isMaxiInterestAGoGo()) {
            return amount * 0.05;
        }
        else {
        	return amount * 0.001;
        }
    }	
	/**
	 * Check if any withdrawals in the past 10 days
	 * @return false if withdrawal within past 10 days; true otherwise
	 */
	private boolean isMaxiInterestAGoGo() {
		boolean result = true;
		long diff = 0;
		for (Map.Entry<Transaction, Integer> entry: getTransactions().entrySet()) {	
			diff = DateProvider.getInstance().now().getTime()-entry.getKey().getTransactionDate().getTime();
			diff = TimeUnit.MILLISECONDS.toDays(diff);
			if((diff < 10) && (entry.getKey().getAmount() < 0)) {			
				result = false;
			}
		}
		return result;
	}

}
