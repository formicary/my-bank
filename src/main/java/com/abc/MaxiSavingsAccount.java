package com.abc;

import java.util.List;

public class MaxiSavingsAccount extends Account {

	public MaxiSavingsAccount() {
		super();
		this.setInterestRate(2);
	}

	@Override
	public double interestEarned(double principal, double rate, double compoundTimes, double periodInYears) {
		double p = principal; // principal amount
		if (p <= 1000) {
			this.setInterestRate(2);// maxi-savings account have 2 % for the first $1000
			rate = 2;
		} else if (p <= 2000) {
			if (checkWithdrawals(10) == false) {
				// Change Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals in the past 10
				// days
				this.setInterestRate(5);
				rate = 5;
			} else {
				this.setInterestRate(0.1); // otherwise 0.1%
				rate = 0.1;
			}
		} else {
			this.setInterestRate(10);
			rate = 10;
		}
		double r = rate;// interest rate as a percentage not decimal!
		double n = compoundTimes;// compounded 365 times a year
		double t = periodInYears; // time period in years,
		double result = (p * Math.pow((1 + r / (100 * n)), n * t)) - p;
		return result;
	}

	/**
	 * Checks for withdrawals for the past number of days
	 * 
	 * @param days
	 *            number of days
	 * @return True if there were at least one withdrawal otherwise False
	 */
	private boolean checkWithdrawals(int days) {
		List<Transaction> l = this.getTransactions(days);
		if (l.isEmpty()) { // if t is empty that means customer did not make any transactions for the past param days
			return false;
		} else {
			for (Transaction t : l) { // need to find at least one withdrawal transaction
				if (t.getTransactionType() == "withdraw") {
					return true;
				}
			}
		}
		return false;
	}
}
