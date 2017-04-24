package com.abc.implementation;

import com.abc.interfaces.IRateHelper;
import com.abc.interfaces.ITransaction;
import com.abc.interfaces.ITransactionHelper;
import com.abc.utilities.AccountType;

public class MaxiSavingsAccount extends Account {
	private ITransactionHelper transactionHelper;
	private IRateHelper rateHelper;
	
	private final double YEARLY_NO_WITHDRAWALS = 0.05;
	private final double YEARLY_WITHDRAWALS = 0.001;
	private final int PERIOD = 10;
	
	public MaxiSavingsAccount(IRateHelper rateHelper, ITransactionHelper transactionHelper) {
		super(AccountType.MAXI_SAVINGS);
		this.transactionHelper = transactionHelper;
		this.rateHelper = rateHelper;
	}
	
	public MaxiSavingsAccount(ITransaction rootTransaction, IRateHelper rateHelper, ITransactionHelper transactionHelper) {
		super(AccountType.MAXI_SAVINGS);
		this.transactionHelper = transactionHelper;
		this.rateHelper = rateHelper;
		this.rootTransaction = rootTransaction;
		this.currentTransaction = rootTransaction;
	}

	@Override
	public double compoundInterestEarned() {
		int daysSinceLastTransaction = 0;
		double totalAmount = 0;
		double totalInterest = 0;
		double dailyRateNoWithdrawals = rateHelper.getDailyRate(YEARLY_NO_WITHDRAWALS);
		double dailyRateWithdrawals = rateHelper.getDailyRate(YEARLY_WITHDRAWALS);
		
		ITransaction transaction = getRootTransaction();
		
		while(transaction != null)
		{
			ITransaction nextTransaction = transaction.getNextTransaction();
			double transactionAmount = transaction.getAmount();
			totalAmount += transactionAmount;			
			daysSinceLastTransaction = transactionHelper.getDaysDifference(transaction, nextTransaction);
			double interest;
			if(transactionHelper.isWithdrawnWithin(transaction, PERIOD)) {
				interest = totalAmount * rateHelper.getEarnedInterest(dailyRateWithdrawals, daysSinceLastTransaction);
				totalInterest += interest;
			} else {
				interest = totalAmount * rateHelper.getEarnedInterest(dailyRateNoWithdrawals, daysSinceLastTransaction);
				totalInterest += interest;
			}
			transaction = nextTransaction;
			totalAmount += interest;
		}
		
		return totalInterest;
	}	

}
