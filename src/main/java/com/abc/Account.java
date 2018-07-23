package com.abc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Account {

	private final AccountType accountType;
	private double balance;
	private static final AtomicInteger UNIQUE_ID = new AtomicInteger();
	private final int id;
	public Map<Transaction, Date> transactions;

	public Account(AccountType accountType) {
		this.accountType = accountType;
		this.transactions = new HashMap<Transaction, Date>();
		this.id = UNIQUE_ID.getAndIncrement();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.put(new Transaction(amount), new Date());
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else if (amount > balance) {
			throw new IllegalArgumentException("Insufficient amount of balance");
		} else {
			transactions.put(new Transaction(-amount), new Date());
		}
	}

// calculate the difference of two date 
	public static int getTimeDiff(Date transactionDate, Date today) {

		long timeDiff = Math.abs(today.getTime() - transactionDate.getTime());
		long dayDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
		return (int) dayDiff;
	}

	public boolean isWithdrawWithinTendays() {
		for (Entry<Transaction, Date> entry : transactions.entrySet()) {
			// negative amount indicate the withdraw
			if (entry.getKey().amount < 0 && getTimeDiff(entry.getValue(), new Date()) < 10) {
				return true;
			}
		}
		return false;
	}

	public double interestEarned() {
		double amount = getBalance();
		switch (accountType) {
		case CHECKING:
			return amount * 0.001;
		case SAVINGS:
			if (amount <= 1000)
				return amount * 0.001;
			else
				return 1 + (amount - 1000) * 0.002;
		case MAXI_SAVINGS:
			if (amount <= 1000)
				return amount * 0.02;
			if (amount <= 2000)
				return 20 + (amount - 1000) * 0.05;
			return 70 + (amount - 2000) * 0.1;
		default:
			throw new IllegalArgumentException("Unrecognized Account Type");
		}
	}

	public double interestEarnedDaily() {
		double amount = getBalance();
		switch (accountType) {
		case CHECKING:
			return amount * 0.001 / 365;
		case SAVINGS:
			if (amount <= 1000)
				return amount * 0.001 / 365;
			else
				return (1 + (amount - 1000) * 0.002) / 365;
		case MAXI_SAVINGS:
			// withdrawals in the past 10 days
			if (isWithdrawWithinTendays()) {
				return amount * 0.001 / 365;
			} else {
				return amount * 0.05 / 365;
			}
		default:
			throw new IllegalArgumentException("Unrecognized Account Type");
		}
	}

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
//        for (Transaction t: transactions)
//            amount += t.amount;
		return amount;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public double getBalance() {
		return this.balance;
	}

	public int getId() {
		return this.id;
	}

}
