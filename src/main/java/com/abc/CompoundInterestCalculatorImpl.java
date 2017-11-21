package com.abc;

public class CompoundInterestCalculatorImpl implements CompoundInterestCalculator {
	
	
	
	public double getCompoundInterest(double p, int t, double r, double n) {
		double amount = p * Math.pow((1 + (r / t)), (n * t));

		double interest = amount - p;
		return interest;

	}
	
	
	 
	 

}
