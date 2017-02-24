package com.mybank.calculators;

import java.util.List;

import com.mybank.entities.Transaction;

public class MaxiSavingsAccountCalculator extends InterestCalculator {

	private final double yearlyRate = 0.02;
	private final double yearlyRateAboveAThousand = 0.05;
	private final double yearlyRateAboveTwoThousand = 0.1;
	private double dailyRate;
	private double dailyRateAboveAThousand;
	private double dailyRateAboveTwoThousand;
	
	public MaxiSavingsAccountCalculator() {
		
		dailyRate = calculateDailyRate(yearlyRate);
		dailyRateAboveAThousand = calculateDailyRate(yearlyRateAboveAThousand);
		dailyRateAboveTwoThousand = calculateDailyRate(yearlyRateAboveTwoThousand);
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
			} else if(capital+interest < 2000) {
				
				interest += 1000 * (Math.pow(1+dailyRate, days)-1) +(capital-1000 + interest ) * (Math.pow((1+dailyRateAboveAThousand), days) -1);
			} else {
				
				interest += 1000 * (Math.pow(1+dailyRate, days)-1) + 1000*(Math.pow(1+dailyRateAboveAThousand, days)-1) +(capital-2000 + interest ) * (Math.pow((1+dailyRateAboveTwoThousand), days) -1);
			}
		}
		
		return interest;
	}
	
	

}
