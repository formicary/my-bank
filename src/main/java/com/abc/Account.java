package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Account {

	private final Customer customer;
	private final AccountType accountType;
	public List<Transaction> transactions;
	private double balance;

	public enum AccountType {
		CHECKING("Checking Account"), 
		SAVINGS("Savings Account"), 
		MAXI_SAVINGS("Maxi Savings Account");

		private final String name;

		AccountType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public Account(Customer customer, AccountType accountType) {
		this.customer = customer;
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		} else {
			transactions.add(new Transaction(Transaction.TransactionType.DEPOSIT, amount));
			balance += amount;
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}
		if (balance < amount) {
			throw new IllegalArgumentException("Insufficient Balance.");
		}
		transactions.add(new Transaction(Transaction.TransactionType.WITHDRAW, amount));
		balance -= amount;
	}

	public abstract double interestEarned();

	public double sumTransactions() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.getAmount();
		return amount;
	}

	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(transactions);
	}

	public Customer getCustomer() {
		return customer;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public double getBalance() {
		return balance;
	}

}
