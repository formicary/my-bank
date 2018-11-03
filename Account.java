package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
	private List<Transaction> transactions;
	private final AccountType accountType;
	
	
	public Account(AccountType accountType) {
		this.transactions = new ArrayList<Transaction>();
		this.accountType = accountType;
	}
		
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	
	
	public String getAccountTypeString() {
		return accountType.toString();
	}
	

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}
	
    // can no longer withdraw if ammount is greater than the account balance.
	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else if (amount > sumTransactions()) {
			throw new IllegalArgumentException("amount must be greater than balance in account");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}
	
	
	public double interestEarned(InterestCalculator calculator) {
		if (transactionsExist()) {  // if no transactions exist on account return 0 instead of executing other methods.
			switch (accountType) {
			case SAVINGS:
				return calculator.calcSavingsInterest(this);
			case MAXI_SAVINGS:
				return calculator.calcMaxiSavingsInterest(this);
			default:
				return calculator.calcDefaultInterest(this);
			}
		}
		return 0;
	}

	
	private boolean transactionsExist() {
		return !transactions.isEmpty();
	}
	
	
	public double sumTransactions() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.getAmount();
		return amount;
	}
}
