package com.abc;

public class MaxiSavingsAccount extends Account{

	@Override
	public String toString() {
		return "Maxi-Savings Account";
	}

	@Override
	public double getInterest() {
		double sum = sumTransactions();
		
		if (sum <= 1000) {
			return sum * 0.02;
		}
		else if (sum <= 2000) {
			return 20 + (sum-1000) * 0.05;
		}
		else {
			return 70 + (sum-2000) * 0.1;
		}
	}
}
