package com.abc;
public class SavingsAccount extends Account {

	public SavingsAccount() {
		super();
		this.setInterestRate(0.1);
	}

	@Override
	public double interestEarned(double principal, double rate, double compoundTimes, double periodInYears) {
		double p = principal; // principal amount
		if (p <= 1000) {
			this.setInterestRate(0.1);// savings account have 0.1 % for the first $1000
			rate = 0.1;
		} else {
			this.setInterestRate(0.2);
			rate = 0.2;
		}
		double r = rate;// interest rate as a percentage not decimal!
		double n = compoundTimes;// compounded 365 times a year
		double t = periodInYears; // time period in years,
		double result = (p * Math.pow((1 + r / (100 * n)), n * t)) - p;
		return result;
	}
}
