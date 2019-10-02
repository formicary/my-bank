package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

	public List<Transaction> transactions;
	enum accountTypes {Checking, Savings, Maxi_Savings};
	private final accountTypes type;
	private double balance = 0.0;
	
	public Account(accountTypes accType) {
		this.type = accType;
		this.transactions = new ArrayList<Transaction>();
		balance = 0.0;
	}

	public void deposit(double amount) {
		if (positiveAmount(amount)) {
			transactions.add(new Transaction(amount));
			balance += amount;
		}
	}

	public void withdraw(double amount) {
		if (positiveAmount(amount) && (amount <= this.getBalance())) {
			transactions.add(new Transaction(-amount));
			balance -= amount;
		} else {
			throw new IllegalArgumentException("amount must be less than or equal to balance");
		}
	}
	
	public double getBalance() {
		return balance;
	}

	private boolean positiveAmount(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			return true;
		}
	}
	public abstract double interestEarned();
	
	public String getAccountType() {
		return type.toString();
	}
	
}
