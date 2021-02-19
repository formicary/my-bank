package com.abc;

import java.util.Date;

public class Transaction {

	public final double amount;
	private Date transactionDate;
	private int accountType;

	public Transaction(double amount,int accountType) {
		this.amount = amount;
		this.transactionDate = DateProvider.getInstance().now();
		this.accountType=accountType;
	}

}
