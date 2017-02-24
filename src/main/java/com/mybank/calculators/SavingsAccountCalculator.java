package com.mybank.calculators;

import java.util.List;

import com.mybank.entities.Transaction;

public class SavingsAccountCalculator extends InterestCalculator {

	private final double yearlyRate = 0.001;
	private final double yearlyRateAboveAThousand = 0.002;
	private double dailyRate;
	private double dailyRateAboveAThousand;

	public SavingsAccountCalculator() {
		
		dailyRate = calculateDailyRate(yearlyRate);
		dailyRateAboveAThousand = calculateDailyRate(yearlyRateAboveAThousand);
	}
	
	@Override
	public double calculateInterest(List<Transaction> transactions) {
		double interest = 0;
		double capital = 0;

		for (int i = 0; i < transactions.size(); i++) {
			Transaction currentTransaction = transactions.get(i);
			capital += currentTransaction.getTransactionAmount();
			long days = currentTransaction.getDaysToNextTransaction(getNextTransaction(i, transactions));
			if(capital + interest < 1000){
				interest += (capital+interest) * (Math.pow((1+dailyRate), days) -1);
			} else {
				
				interest += 1000 * (Math.pow(1+dailyRate, days)-1) +(capital-1000 + interest ) * (Math.pow((1+dailyRateAboveAThousand), days) -1);
			}
		}
		
		return interest;
	}

}
