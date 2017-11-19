package com.abc;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class Account {

	enum Type {
		CHECKING("Checking Account", money -> money * 0.001), 
		SAVINGS("Savings Account", money -> {
			if (money <= 1000)
				return money * 1000.0;
			else {
				return 1 + (money - 1000) * 0.002;
			}
		}), 
		MAXI_SAVINGS("Maxi Savings Account", money -> {
			if (money <= 1000)
				return money * 0.02;
			if (money <= 2000)
				return 20 + (money - 1000) * 0.05;
			return 70 + (money - 2000) * 0.1;
		});

		public final String name;
		public final Function<? super Double, ? extends Double> interest;

		private Type(String name, Function<? super Double, ? extends Double> interest) {
			this.name = name;
			this.interest = interest;
		}

	}

	private final Type accountType;
	public List<Transaction> transactions;
	private double sumMoney;

	public double getSumMoney() {
		return sumMoney;
	}

	public Account(Type accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.sumMoney = 0.0;
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
			sumMoney += amount;
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
			sumMoney -= amount;
		}
	}

	public double computeInterestEarned() {
		return accountType.interest.apply(sumTransactions());
	}

	public double sumTransactions() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.getAmount();
		return amount;

	}

	public String getStatement() {
		StringBuilder str = new StringBuilder();
		NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);

		str.append(getAccountType().name).append("\n");
		for (Transaction t : transactions) {
			str.append(String.format("%-15s%10s", t.getAmount() < 0.0 ? "withdrawal" : "deposit", us.format(Math.abs(t.getAmount()))));
			str.append("\n");
		}
		str.append("Total ").append(us.format(sumMoney));
		
		return str.toString();
	}



	public Type getAccountType() {
		return accountType;
	}

}
