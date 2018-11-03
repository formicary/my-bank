package com.abc;

public class YearlyInterestCalculator implements InterestCalculator {
	
	
	/*
	 * Original interest rate calculations.
	 */
	@Override
	public double calcSavingsInterest(Account account) {
		double amount = account.sumTransactions();

		if (amount <= 1000)
            return amount * 0.001;
        else
            return 1 + (amount-1000) * 0.002;	
	}
	
	
	@Override
	public double calcMaxiSavingsInterest(Account account) {
		double amount = account.sumTransactions();
		if (amount <= 1000)
            return amount * 0.02;
        if (amount <= 2000)
            return 20 + (amount-1000) * 0.05;
        return 70 + (amount-2000) * 0.1;
	}
	
	
	@Override
	public double calcDefaultInterest(Account account) {
		double amount = account.sumTransactions();
		return amount * 0.001;
	}
}
