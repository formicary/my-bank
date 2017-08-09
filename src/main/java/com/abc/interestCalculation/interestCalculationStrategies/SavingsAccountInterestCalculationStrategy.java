package com.abc.interestCalculation.interestCalculationStrategies;

import com.abc.Account;

public class SavingsAccountInterestCalculationStrategy implements InterestCalculationStrategy{

	private double savingsAnnualInterestRateLow = 0.001;
	private double savingsAnnualInterestRateHigh = 0.002;

	public double calculateInterest(Account account) {
		
		double accountBalance = account.getCurrentBalance();
		long calculationDays = account.getDaysSinceLastTransaction();
		
		for (int i = 0; i < calculationDays; i++) {
			if (accountBalance >= 1000) {
				
				accountBalance += 1000 * (Math.pow(1.0 + savingsAnnualInterestRateLow, 1.0 / 365.0) - 1)
						+ (accountBalance - 1000) * (Math.pow(1.0 + savingsAnnualInterestRateHigh, 1.0 / 365.0) - 1);
			
			} else {
				
				accountBalance += accountBalance * (Math.pow(1.0 + savingsAnnualInterestRateLow, 1.0 / 365.0) - 1);
			
			}
		}
		
		return accountBalance - account.getCurrentBalance();
	}

}
