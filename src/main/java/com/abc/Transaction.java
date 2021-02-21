package com.abc;

import java.util.Date;

public class Transaction {

	public final double amount;
	private Date transactionDate;
	private Long transactionDateMillSec;
	private int accountType;

	public Transaction(double amount,int accountType) {
		this.amount = amount;
		this.transactionDate = DateProvider.getInstance().now();
		this.accountType=accountType;
		this.transactionDateMillSec = DateProvider.getInstance().nowMillSec();
	}

	public double getAmount() {
		return amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public int getAccountType() {
		return accountType;
	}

	public Long getTransactionDateMillSec() {
		return transactionDateMillSec;
	}


}
