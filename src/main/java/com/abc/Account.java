package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public abstract class Account {

	// Types of accounts.
	public static final String CHECKING = "CHECKING";
	public static final String SAVINGS = "SAVINGS";
	public static final String MAXI_SAVINGS = "MAXI SAVINGS";
	protected String accountType;
	private List<Transaction> transactions;
	// The opening day of the account.
	private Date openingDate;
	// Total amount of interest earned until this moment.
	protected double totalInterestAmount;
	// The time in milliseconds from the last update call until now.
	protected long lastUpdate;
	// Temporary amount of interest recalculated until the last transaction in a day
	// occurs.
	protected double tempInterest;

	public Account() {
		openingDate = (new DateHelper()).now();
		this.transactions = new ArrayList<Transaction>();
	}

	// Updates the current interest.
	// Implemented by each type of account.
	public abstract void update(long time, double amount);

	// Add funds to the account.
	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero.");
		} else {
			Transaction t = new Transaction(amount);
			update(t.getTransactionDate().getTime(), t.getAmount());
			transactions.add(t);
		}
	}

	// Add funds to the account on a specified date.
	// Used for testing and admin purposes.
	public void depositTest(double amount, Date testDate) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero.");
		} else {
			Transaction t = new Transaction(amount);
			update(testDate.getTime(), t.getAmount());
			transactions.add(t);
		}
	}

	// Subtract funds from the account.
	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero.");
		} else if (getBalance() < amount) {
			throw new IllegalArgumentException("Amount must be smaller than balance.");
		} else {
			Transaction t = new Transaction(-amount);
			update(t.getTransactionDate().getTime(), t.getAmount());
			transactions.add(t);

		}
	}

	// Subtract funds to the account on a specified date.
	// Used for testing and admin purposes.
	public void withdrawTest(double amount, Date testDate) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero.");
		} else if (getBalance() < amount) {
			throw new IllegalArgumentException("Amount must be smaller than balance.");
		} else {
			Transaction t = new Transaction(-amount);
			update(testDate.getTime(), t.getAmount());
			transactions.add(t);

		}
	}

	// Returns the balance of the current account.
	public double getBalance() {
		double amount = 0.0;
		for (Transaction t : transactions) {
			amount += t.getAmount();
		}
		return amount;
	}

	// Returns the list of transactions of the current account.
	public List<Transaction> getTransactions() {
		return transactions;
	}

	// Returns the total amount of interest earned on this account by today.
	public double interestEarned() {
		long today = (new DateHelper()).now().getTime();
		update(today, 0.0);
		return getTotalInterestEarned();
	}

	// Returns the account type.
	public abstract String getAccountType();

	// Returns the current amount of interest earned on this account.
	public abstract double getTotalInterestEarned();

	// Returns the opening date of the account.
	public long getOpeningDate() {
		return openingDate.getTime();
	}

}
