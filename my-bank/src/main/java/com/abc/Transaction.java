package com.abc;

import java.util.Date;

public class Transaction {
	public final double amount;
	final TransactionType transactionType;
	private Date transactionDate;

	public enum TransactionType {
		WITHDRAW, DEPOSIT;
	}

	public Transaction(TransactionType transactionType, double amount) {
		this.transactionType = transactionType;
		this.amount = amount;
		this.transactionDate = DateProvider.getInstance().now();
	}

	public double getAmount() {
		if (transactionType == TransactionType.WITHDRAW) {
			return -amount;
		}
		return amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}
}
