package com.abc.implementation;

import com.abc.AccountType;
import com.abc.interfaces.IRateHelper;
import com.abc.interfaces.ITransaction;
import com.abc.interfaces.ITransactionHelper;

public class SavingsAccount extends Account {

	private IRateHelper rateHelper;
	private ITransactionHelper transactionHelper;
	
	private final double yearlyRateBelow1000 = 0.001;
	private final double yearlyRateAbove1000 = 0.002;
	
	
	
	
	public SavingsAccount(IRateHelper rateHelper, ITransactionHelper transactionHelper) {
		super(AccountType.SAVINGS);
		this.rateHelper = rateHelper;
		this.transactionHelper = transactionHelper;
	}

	@Override
	public double compoundInterestEarned() {
		
		int daysSinceLastTransaction = 0;
		double totalAmount = 0;
		double totalInterest = 0;
		double dailyRateBelow1000 = rateHelper.getDailyRate(yearlyRateBelow1000);
		double dailyRateAbove1000 = rateHelper.getDailyRate(yearlyRateAbove1000);
		
		ITransaction transaction = getRootTransaction();
		
		while(transaction != null)
		{
			ITransaction nextTransaction = transaction.getNextTransaction();
			double transactionAmount = transaction.getAmount();
			totalAmount += transactionAmount;			
			daysSinceLastTransaction = transactionHelper.getDaysDifference(transaction, nextTransaction);
			double interest;
			if(totalAmount <= 1000){
				interest = totalAmount * rateHelper.getEarnedInterest(dailyRateBelow1000, daysSinceLastTransaction);
				totalInterest += interest;
			} else {
				interest = 1000 *  rateHelper.getEarnedInterest(dailyRateBelow1000, daysSinceLastTransaction) +(totalAmount - 1000 ) * rateHelper.getEarnedInterest(dailyRateAbove1000, daysSinceLastTransaction);
				totalInterest += interest;
			}
			transaction = nextTransaction;
			totalAmount += interest;
		}
		
		return totalInterest;
	}

}
