package com.abc;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Transaction {

	public final double amount;
	private LocalDate transactionLocalDate;
	private String accountType;

	public Transaction(double amount, String accountType) {
		this.amount = amount;
		this.accountType = accountType;
		this.transactionLocalDate = DateProvider.getInstance().now();
	}

	public double getAmount() {
		return amount;
	}

	public String getAccountType() {
		return accountType;
	}

	public LocalDate getTransactionLocalDate() {
		return transactionLocalDate;
	}

}
