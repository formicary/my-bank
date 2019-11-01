package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
	public final double amount;
	// added transaction type for the second added feature
	public static final int DEPOSIT = 0;
	public static final int WITHDRAWAL = 1;
	private final int transactionType;
	private Date transactionDate;

	public Transaction(double amount, int type) {
		this.amount = amount;
		this.transactionDate = Calendar.getInstance().getTime();
		this.transactionType = type;
	}

	public Date getDate() {
		return transactionDate;
	}

	public int getTransactionType() {
		return this.transactionType;
	}

}
