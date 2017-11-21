package com.abc;

public  class SavingsAccount extends Account {
	// composed helper class (i.e. composition).
		CompoundInterestCalculator calculator = new CompoundInterestCalculatorImpl (); 
		double interestRateAboveThousand =0.00;
	

	public SavingsAccount() {
		accountType = 1;
		interestRate = 0.001;
	    interestRateAboveThousand= 0.002;
	}
	
	/**
	 * Calculates the daily compound interest. Will calculate 0.1% for the first $1,000 then 0.2%.
	 */
	
	public double calculateInterestForAccount(double balance, int datedifference) {

		if (balance <= 1000) {
			
		return calculator.getCompoundInterest(balance,days,interestRate , datedifference/365);
		} else {
			double interestOnBelowThousand = calculator.getCompoundInterest(balance,days,interestRate , datedifference/365);
			double onAboveTHousand= calculator.getCompoundInterest(balance-1000,days,interestRateAboveThousand, datedifference/365);
			return interestOnBelowThousand+onAboveTHousand;
		}
	}
	
	
}

	
	
	
	
	
	