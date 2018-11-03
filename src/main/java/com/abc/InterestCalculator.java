package com.abc;


/*
 * Added this interface to allow the calculations for interest rates to be changed easily as 
 * they may change every year. Allows this to happen without having to change
 * implementation of account class.
 */
public interface InterestCalculator {

	public double calcSavingsInterest(Account account);
	
	public double calcMaxiSavingsInterest(Account accouht);
	
	public double calcDefaultInterest(Account account);
	
}
