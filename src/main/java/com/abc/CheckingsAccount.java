
package com.abc;


public class CheckingsAccount extends Account {
	
	// composed helper class (i.e. composition).
	CompoundInterestCalculator calculator = new CompoundInterestCalculatorImpl (); 

	public CheckingsAccount() {
		
		accountType = 0;
		interestRate = 0.001;

	}
	

	public double calculateInterestForAccount(double balance, int datedifference) {
		
		
		return calculator.getCompoundInterest(balance,days,interestRate , datedifference/365);
		
	}
	
}
