package com.abc.implementation;

import com.abc.interfaces.IRateHelper;
import com.abc.interfaces.ITransaction;
import com.abc.interfaces.ITransactionHelper;
import com.abc.utilities.AccountType;

public class CheckingAccount extends Account {

	private final double yearlyRate = 0.001;
	
	
	private IRateHelper rateHelper;
	private ITransactionHelper transactionHelper;
	
	public CheckingAccount(IRateHelper rateHelper, ITransactionHelper transactionHelper) {
		super(AccountType.CHECKING);
		this.rateHelper = rateHelper;
		this.transactionHelper = transactionHelper;
	}

	@Override
	public double compoundInterestEarned() {
		int daysSinceLastTransaction = 0;
		double totalAmount = 0.0;
		double totalInterest = 0.0;
		double dailyRate = rateHelper.getDailyRate(yearlyRate);
		ITransaction transaction = getRootTransaction();
		
		while(transaction != null)
		{
			ITransaction nextTransaction = transaction.getNextTransaction();
			double transactionAmount = transaction.getAmount();
			totalAmount += transactionAmount;			
			daysSinceLastTransaction = transactionHelper.getDaysDifference(transaction, nextTransaction);
			double interest = totalAmount * rateHelper.getEarnedInterest(dailyRate, daysSinceLastTransaction);
			totalInterest += interest;
			transaction = nextTransaction;
			totalAmount += interest;	
		}
		
		return totalInterest;
	}

}
