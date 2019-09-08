package com.abc;
public class CheckingAccount extends Account {

	public CheckingAccount() {
		super();
		this.setInterestRate(0.1);
	}

	@Override
	public double interestEarned(double principal, double rate, double compoundTimes, double periodInYears) {
		double p = principal; // principal amount
		double r = rate; // interest rate as a percentage not decimal!
		double n = compoundTimes; // compounded 365 times a year
		double t = periodInYears; // time period in years,
		double result = (p * Math.pow((1 + r / (100 * n)), n * t)) - p;
		return result;
	}
}
