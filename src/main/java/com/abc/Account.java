package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

	private final AccountType accountType;
	private double balance;
	private static final AtomicInteger UNIQUE_ID = new AtomicInteger();
	private final int id;
	private ReentrantLock accountLock;

	public List<Transaction> transactions;

	public Account(AccountType accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.id = UNIQUE_ID.getAndIncrement();
		accountLock = new ReentrantLock();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			try {
				accountLock.lock();
				transactions.add(new Transaction(amount));
				this.balance += amount;
			} finally {
				accountLock.unlock();
			}
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else if (amount > balance) {
			throw new IllegalArgumentException("Insufficient amount of balance");
		} else {
			try {
				accountLock.lock();
				transactions.add(new Transaction(-amount));
				this.balance -= amount;
			} finally {
				accountLock.unlock();
			}
		}
	}

// calculate the difference of two date 
	public static int getTimeDiff(Date transactionDate, Date today) {

		long timeDiff = Math.abs(today.getTime() - transactionDate.getTime());
		long dayDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
		return (int) dayDiff;
	}

	public boolean isWithdrawWithinTendays() {
		for (Transaction t : transactions) {
			// negative amount indicate the withdraw
			if (t.getAmount() < 0 && getTimeDiff(t.getTransactionDate(), new Date()) < 10) {
				return true;
			}
		}
		return false;
	}

	// Interest calculate once a year
	public double interestEarned() {

		switch (accountType) {
		case CHECKING:
			return balance * 0.001;
		case SAVINGS:
			if (balance <= 1000)
				return balance * 0.001;
			else
				return 1 + (balance - 1000) * 0.002;
		case MAXI_SAVINGS:
			if (balance <= 1000)
				return balance * 0.02;
			if (balance <= 2000)
				return 20 + (balance - 1000) * 0.05;
			return 70 + (balance - 2000) * 0.1;
		default:
			throw new IllegalArgumentException("Unrecognized Account Type");
		}
	}

	// Interest calculate every single day, assume 1 year = 365 days
	public double interestEarnedDaily() {
		switch (accountType) {
		case CHECKING:
			return balance * 0.001 / 365;
		case SAVINGS:
			if (balance <= 1000)
				return balance * 0.001 / 365;
			else
				return (1 + (balance - 1000) * 0.002) / 365;
		case MAXI_SAVINGS:
			// withdrawals in the past 10 days
			if (isWithdrawWithinTendays()) {
				return balance * 0.001 / 365;
			} else {
				return balance * 0.05 / 365;
			}
		default:
			throw new IllegalArgumentException("Unrecognized Account Type");
		}
	}

	public void accumulateBalanceDaily() {
		this.balance += interestEarnedDaily();
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
