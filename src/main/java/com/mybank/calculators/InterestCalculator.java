package com.mybank.calculators;

import java.util.GregorianCalendar;
import java.util.List;

import com.mybank.entities.Transaction;

public abstract class InterestCalculator {
	
	protected GregorianCalendar getNextTransaction(int i, List<Transaction> transactions) {
		if ((i+1)<transactions.size())
			return transactions.get(i+1).getTransactionDateTime();
		else
			return new GregorianCalendar();

	}
	
	protected double calculateDailyRate(double yearlyRate){
		return Math.pow((1 + yearlyRate), 1.0/365.0) - 1;
	}
	
	public abstract double calculateInterest(List<Transaction> transactions);
}
