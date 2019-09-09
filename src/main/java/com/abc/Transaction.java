package com.abc;

import java.util.Date;

/**
 * Transactions are kept by an account to record its transaction history.
 */
public class Transaction {

	/**
	 * A double to keep track of the amount that is deposited or withdrawn.
	 * 
	 * A string to determine the transaction type, there are many types but for this exercise there are only two :
	 * withdraw and deposit
	 * 
	 * A date to record when the transaction was made.
	 * 
	 */
	private final double amount;
	private String transactionType;
	private Date transactionDate;

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
