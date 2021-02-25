package com.abc;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {

	private String accountType;
	public List<Transaction> transactions;
	private double balance;

	public Account(String accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.balance = 0.0;
	}

	public String deposit(double amount, String accountType) {
		if (amount <= 0.0 || accountType != this.accountType)
			return "amount must be greater than zero";
			this.balance += amount;
			transactions.add(new Transaction(amount, accountType));
			return "Deposit";
	}

	public String withdraw(double amount, String accountType) {
		if (amount <= 0.0 || amount > getBalance() || accountType != this.accountType)
			return ("amount must be greater than zero and less than balance");
			balance -= amount;
			transactions.add(new Transaction(-amount, accountType));
			return "Withdraw";
		
	}

	public abstract double interestEarned(Account account, double amount);

	private void calculateDailyInterest(Account account, double amount) {
		if (transactions.size() > 0 && balance != 0.0) {
			int days = getPeriod(transactions.get(transactions.size() - 1).getTransactionLocalDate());
			for (int i = 0; i < days; i++) {
				balance+=interestEarned(account, amount);
			}
		}
	}

	protected int getPeriod(LocalDate localDateTransaction) {
		Period per = Period.between(LocalDate.now(), localDateTransaction);
		return per.getDays();
	}

	public String getAccountType() {
		return accountType;
	}

	public double getBalance() {
		return this.balance;
	}

}
