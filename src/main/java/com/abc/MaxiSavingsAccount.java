package com.abc;

public class MaxiSavingsAccount extends Account{

	private final String ACCOUNT_NAME = "Maxi Savings Account";


	@Override
	public double interestEarned() {
						 
		long tenDaysInMilliseconds = 10 * 24 * 60 * 60 * 1000;
		// no withdrawals in the past 10 days
		if(DateProvider.getInstance().now().getTime() - getLastWithdrawDate().getTime() > tenDaysInMilliseconds) {
			return sumTransactions() * 0.05;
		}	
		return sumTransactions() * 0.001;
	}
		
	@Override
	public String getAccountName() {
		return ACCOUNT_NAME;
	}
}
