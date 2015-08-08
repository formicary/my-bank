package com.abc.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

public class Account{

	private final AccountType accountType;
	private final List<Transaction> transactionList;

	public Account(AccountType accountType) {
		Objects.requireNonNull(accountType, "Type of the account is required");
		this.accountType = accountType;
		this.transactionList = new ArrayList<Transaction>();
	}

	

	/*public double interestEarned() {
		double amount = sumTransactions();
		switch (accountType) {
		case SAVINGS:
			if (amount <= 1000)
				return amount * 0.001;
			else
				return 1 + (amount - 1000) * 0.002;
			// case SUPER_SAVINGS:
			// if (amount <= 4000)
			// return 20;
		case MAXI_SAVINGS:
			if (amount <= 1000)
				return amount * 0.02;
			if (amount <= 2000)
				return 20 + (amount - 1000) * 0.05;
			return 70 + (amount - 2000) * 0.1;
		default:
			return amount * 0.001;
		}
	}*/


	public Money getBalance() {
		Money money = Money.ZERO_USD;
		for(final Transaction transaction : transactionList){
			money = money.add(transaction.getMoney());
		}
		return money;
	}
	
	
	public Money getIntrestPaid() {
		Money money = Money.ZERO_USD;
		for(final Transaction transaction : transactionList){
			if(transaction.isInterest()){
				money = money.add(transaction.getMoney());
			}
		}
		return money;
	}


	public AccountType getAccountType() {
		return accountType;
	}

	public List<Transaction> getTransactionList() {
		return transactionList;
	}
	
	public void addTransaction(Transaction transaction){
		Objects.requireNonNull(transaction);
		transactionList.add(transaction);
	}
	
}
