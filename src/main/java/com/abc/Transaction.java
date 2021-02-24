package com.abc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Transaction {

	public final double amount;
	private Date transactionDate;
	private LocalDate transactionLocalDate;
	private int accountType;

	public Transaction(double amount, int accountType) {
		this.amount = amount;
		this.accountType = accountType;
		this.transactionDate = DateProvider.getInstance().now();
		this.transactionLocalDate = nowLocalDate();
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

	public LocalDate getTransactionLocalDate() {
		return transactionLocalDate;
	}

	private LocalDate nowLocalDate() {
		return getTransactionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

}
