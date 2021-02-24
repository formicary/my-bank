package com.abc;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	public List<Transaction> transactions;
	private double balance;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.balance = 0.0;
	}

	public void deposit(double amount, int accountType) {
		if (amount <= 0.0 || accountType != this.accountType) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			calculateDailyInterest();
			balance += amount;
			transactions.add(new Transaction(amount, accountType));
		}
	}

	public void withdraw(double amount, int accountType) {
		if (amount <= 0.0 || amount > getBalance() || accountType != this.accountType) {
			throw new IllegalArgumentException("amount must be greater than zero and less than balance");
		} else {
			calculateDailyInterest();
			balance = balance - amount;
			transactions.add(new Transaction(-amount, accountType));
		}
	}

	public double interestEarned() {
		switch (accountType) {
		case SAVINGS:
			return interestSavings(balance);
		case MAXI_SAVINGS:
			return interestMaxiSavings(balance);
		default:
			return balance * 0.001;
		}

	}

	private double interestMaxiSavings(double amount) {
		// minDays set minimum days without withdraw to earn maxiSavings
		int minDays = 10;
		for (Transaction transaction : transactions) {
			int pastDays = getPeriod(transaction.getTransactionLocalDate());
			if (pastDays < minDays && transaction.getAmount() < 0)
				return amount * 0.001;
		}
		return amount * 0.05;
	}

	private double interestSavings(double amount) {
		return amount <= 1000 ? amount * 0.001 : 1 + (amount - 1000) * 0.002;
	}

	// case of deposit or withdraw -interest calculate for this period, if period>0
	private void calculateDailyInterest() {
		LocalDate localDateTransaction;
		if (transactions.size() > 0 && balance != 0.0) {
			localDateTransaction = convertDateToLocalDate(
					transactions.get(transactions.size() - 1).getTransactionDate());
			int days = getPeriod(localDateTransaction);
			// call interestEarned times=days, for calculate amount,interest rate accrue
			for (int i = 0; i < days; i++) {
				setBalance(interestEarned());
			}
		}

	}

	private int getPeriod(LocalDate localDateTransaction) {
		LocalDate localDateNow = LocalDate.now();
		Period per = Period.between(localDateNow, localDateTransaction);
		return per.getDays();
	}

	private LocalDate convertDateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public int getAccountType() {
		return accountType;
	}

	public double getBalance() {
		return this.balance;
	}

	private void setBalance(double balance) {
		this.balance = balance;
	}

}
