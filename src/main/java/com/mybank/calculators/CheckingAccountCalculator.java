package com.mybank.calculators;

import java.util.List;

import com.mybank.entities.Transaction;

public class CheckingAccountCalculator extends InterestCalculator{
	
	private final double yearlyRate = 0.001;
	private double dailyRate;
	
	public CheckingAccountCalculator(){
		
		dailyRate = calculateDailyRate(yearlyRate);
	}

	@Override
	public double calculateInterest(List<Transaction> transactions) {
		double interest = 0;
		double capital = 0;

		for (int i = 0; i < transactions.size(); i++) {
			Transaction currentTransaction = transactions.get(i);
			capital += currentTransaction.getTransactionAmount();
			long days = currentTransaction.getDaysToNextTransaction(getNextTransaction(i, transactions));
		
			interest += (capital+interest) * (Math.pow((1+dailyRate), days) -1);
			
		}
		return interest;
	}
	

}
