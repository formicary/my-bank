package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	private List<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<>();
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(0, new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(0, new Transaction(-amount));
		}
	}

	public List<Transaction> transactionsLastTenDays(List<Transaction> transactions) {
		List<Transaction> lastTenDays = new ArrayList<>();
		for (Transaction t : transactions) {
			if (t.getTransactionDate().after(DateProvider.tenDaysPast())) {
				lastTenDays.add(t);
			}
		}
		return lastTenDays;
	}

	public double interestEarned() {
		double amount = sumTransactions();
		int count = 0;
		switch (accountType) {
		case SAVINGS:
			if (amount <= 1000)
				return amount * 0.001;
			else
				return 1 + (amount - 1000) * 0.002;
		case MAXI_SAVINGS:
			for (Transaction t : transactionsLastTenDays(transactions)) {
				if (t.getAmount() < 0) {
					count += 1;
				}
			}
			if (count == 0)
				return amount * 0.05;
			else
				return amount * 0.001;
		default:
			return amount * 0.001;
		}
	}

	public double sumTransactions() {
		return checkIfTransactionsExist();
	}

	private double checkIfTransactionsExist() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}

	public int getAccountType() {
		return accountType;
	}

}
