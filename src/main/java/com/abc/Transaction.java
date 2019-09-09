package com.abc;

import java.util.Date;

public class Transaction {

	private final double amount;
	private Date transactionDate;
	private String transactionType;

	public Transaction(double amount) {
		this.amount = amount;
		this.transactionDate = DateProvider.now();
	}

	/*
	 * Getters
	 */
	public double getAmount() {
		return amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	/*
	 * Setters
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String toString() {
		return transactionType + " " + Formatter.toDollars(amount) + " " + Formatter.toSimpleDate(transactionDate);
	}
}
