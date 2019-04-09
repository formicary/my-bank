package com.abc.account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.abc.DateProvider;
import com.abc.Transaction;

public abstract class Account {
	private static final String PREFIX = "  ";
	public List<Transaction> transactions;
	private DateProvider dateProvider;

	public Account(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
		this.transactions = new ArrayList<Transaction>();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		}
		// usually I would get that dateProvider from the DI context
		transactions.add(new Transaction(amount, dateProvider.now()));
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		}

		transactions.add(new Transaction(-amount, dateProvider.now()));
	}

	public abstract double interestEarned();

	public double sumTransactions() {
		return transactions.stream().map(t -> t.amount).collect(Collectors.summingDouble(Double::doubleValue));
	}

	public abstract String getStatement();

	public String getTransactionSummary() {
		StringBuilder sb = new StringBuilder();
		transactions.forEach(t -> {
			sb.append(PREFIX);
			sb.append(t);
		});
		sb.append(String.format("Total %s", CurrencyUtil.toDollars(sumTransactions())));
		return sb.toString();
	}
}
