package com.abc;

public interface CompoundInterestCalculator {


	/*
	 * Formula for compound interest calculation : p = Principal Amount t = Time
	 * Involved in years i.e. 365 days r = Interest Rate as a decimal n = number
	 * of compounding periods per unit t; at the END of each period, i.e. for 10
	 * days of interest period, n=10/365
	 */
	public abstract double getCompoundInterest(double p, int t, double r, double n) ;
	
}
