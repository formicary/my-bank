package com.abc;

public class SavingsAccount extends Account{

	@Override
	public String toString() {
		return "Savings Account";
	}

	@Override
	public double getInterest() {
		double sum = sumTransactions();
		return (sum > 1000) ? 1 + (sum-1000) * 0.002 : sum * 0.001;
	}

}
