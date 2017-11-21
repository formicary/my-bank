package com.abc;

import java.util.Date;

public class MaxiSavingsAccount extends Account{

	// composed helper class (i.e. composition).
		CompoundInterestCalculator calculator = new CompoundInterestCalculatorImpl (); 

	

	public MaxiSavingsAccount() {
		accountType = 2;
		interestRate = 0.001;
	}
	
	/**
	 * Calculates the daily compound interest. Will calculate 0.1% for the first $1,000 then 0.2%.
	 */
	
	public double calculateInterestForAccount(double balance, int datedifference) {
		
		
		Date tenDaysBefore = new Date(System.currentTimeMillis() - 10L * 24 * 3600
				* 1000);
	
		for (int i = 0;i < transactions.size(); i++) {
		if( transactions.get(i).getTransactionDate().compareTo(tenDaysBefore) > 0)
		{
			
		if (!transactions.get(i).getType()) {
							
							
			return calculator.getCompoundInterest(balance,days,interestRate , datedifference/365);
		}
		}
		
		
		}
			
	   interestRate = 0.05;
		
		return calculator.getCompoundInterest(balance,days,interestRate , datedifference/365);

		
	}
	
	
}

	
	
	
	
	
	