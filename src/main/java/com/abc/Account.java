package com.abc;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {

	private String accountType;
	public List<Transaction> transactions;
	private double balance;

	public Account(String accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.balance = 0.0;
	}

	public void deposit(double amount) {
		if (amount <= 0.0)
			throw new IllegalArgumentException("amount must be greater than zero");
		balance += amount;
		transactions.add(new Transaction(amount, accountType));
	}

	public void withdraw(double amount) {
		if (checkAmount(amount))
			throw new IllegalArgumentException("amount must be greater than zero and less than balance");
		balance -= amount;
		transactions.add(new Transaction(-amount, accountType));
	}
	
	public boolean checkAmount(Double amount) {
		return amount <= 0.0 || amount > sumTransactions();
	}

	public double sumTransactions() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}

	public abstract double interestEarned();

	protected int getPeriod(LocalDate localDateTransaction) {
		Period per = Period.between(LocalDate.now(), localDateTransaction);
		return per.getDays();
	}

	public String getAccountType() {
		return accountType;
	}

	public double getBalance() {
		return balance;

	}

}
