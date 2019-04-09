package com.abc;

import java.time.LocalDate;

import com.abc.account.CurrencyUtil;

public final class Transaction {
	public final double amount;

	private final LocalDate transactionDate;

	public Transaction(double amount, LocalDate date) {
		this.amount = amount;
		this.transactionDate = date;
	}

	@Override
	public String toString() {
		return String.format("%s %s\n", amount < 0 ? "withdrawal" : "deposit", CurrencyUtil.toDollars(amount));
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

}
