package com.abc.interestCalculation.interestCalculationStrategies;

import com.abc.Account;

public class CheckingAccountInterestCalculationStrategy implements InterestCalculationStrategy{

	private double checkingAnnualInterestRate = 0.001;
	
	public double calculateInterest(Account account) {
		
		double accountBalance = account.getCurrentBalance();
		long calculationDays = account.getDaysSinceLastTransaction();
		

		accountBalance=accountBalance*Math.pow(1.0 + checkingAnnualInterestRate, calculationDays/365.0);
		
		
		return accountBalance - account.getCurrentBalance();
	}

}
