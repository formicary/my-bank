package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {

	private boolean DEBUG = false;

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	protected final int accountType;
	public List<Transaction> transactions;

	protected Date lastWithdrawal;

	public Account(int accountType, Boolean debugAccount) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.DEBUG = debugAccount;
	}

//  Calculate Interest before performing the deposit.
	public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        	applyInterest();
        	
        	if (DEBUG) {
        		
               transactions.add(new Transaction(amount, Transaction.TransactionType.DEPOSIT, DateProvider.getInstance().oneHundredDaysAgo()));
        	} else { 
        		transactions.add(new Transaction(amount, Transaction.TransactionType.DEPOSIT));
        		
        	}
        }
    }

//  Calculate interest before performing the withdrawal
	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			applyInterest();
			transactions.add(new Transaction(-amount, Transaction.TransactionType.WITHDRAWAL));
			lastWithdrawal = transactions.get(transactions.size() - 1).getTransactionDate();
		}
	}

	public void interest(double amount) {
		if (amount <= 0.0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount, Transaction.TransactionType.INTEREST));
		}
	}

	protected double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	protected int daysSinceLastTransaction() {
		long currentTime = DateProvider.getInstance().now().getTime();
		long lastTransactionTime = transactions.get(transactions.size() - 1).getTransactionDate().getTime();
		long difference = currentTime - lastTransactionTime;
		long differenceDays = (difference / (1000 * 60 * 60 * 24));
		return (int) differenceDays;
	}

//  Apply interest before returning the sum of the transactions. Ensures a fully up to date
//  Statement.
	public double getBalance() {
		applyInterest();
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}

	public int getAccountType() {
		return accountType;
	}

	protected abstract void applyInterest();

	public double interestEarned() {
		double amount = 0.0;
		applyInterest();
		for (Transaction t : transactions) {
			if (t.getTransactionType() == Transaction.TransactionType.INTEREST)
				amount += t.amount;
		}
		return amount;
	}
}
