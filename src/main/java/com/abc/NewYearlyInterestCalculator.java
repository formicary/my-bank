package com.abc;

import java.time.LocalDateTime;
import java.util.List;


/*
 * New interest rate calculations. Maxi savings account now have an interest rate of 5% 
 * assuming no withdrawals in the past 10 days otherwise 0.1%
 */
public class NewYearlyInterestCalculator implements InterestCalculator {
	
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
		List<Transaction> transactions = account.getTransactions();
		if(lastTxGreaterTenDays(transactions)) 
			return amount * 0.05;
		else
			return amount * 0.001;
	}
	
	
	private boolean lastTxGreaterTenDays(List<Transaction> transactions) {
		LocalDateTime today = DateProvider.getInstance().now();
		LocalDateTime lastTransactionDate = getLastWithdrawal(transactions).getDate();
		int diff = (int) (today.getDayOfYear() - lastTransactionDate.getDayOfYear());
		return diff >= 10 ? true : false;
	}
	
	
	private Transaction getLastWithdrawal(List<Transaction> transactions) {
		for(int i = transactions.size() - 1; i >= 0; i--) {
			if(transactions.get(i).getAmount() < 0 )
				return transactions.get(i);
		}
		return transactions.get(0); //Return the date of the first transaction if there are no withdrawals on account.
	}

	
    @Override	
	public double calcDefaultInterest(Account account) {
		double amount = account.sumTransactions();
		return amount * 0.001;
	}

}
