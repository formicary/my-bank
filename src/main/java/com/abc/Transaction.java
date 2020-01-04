package com.abc;

import java.util.Date;

public class Transaction {
	
	private final double amount;
	private final Date transactionDate;

	public Transaction(double amount) {
		this.amount = amount;
		this.transactionDate = (new DateHelper()).now();
	}

	public Transaction(double amount, Date dateTest) {
		this.amount = amount;
		this.transactionDate = dateTest;
	}

	// Return the amount of a transaction.
	public double getAmount() {
		return amount;
	}

	// Return the date of a transaction.
	public Date getTransactionDate() {
		return transactionDate;
	}
}
