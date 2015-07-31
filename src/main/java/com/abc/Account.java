package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	public List<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public void deposit(double amount) {
		if (isValidAmount(amount)) {
			transactions.add(new Transaction(amount));
		} else {
			return;
		}
	}

	public void withdraw(double amount) {
		if (isValidAmount(amount) && isWithdrawExist(amount)) {
			transactions.add(new Transaction(-amount));

		}
	}

	public abstract double interestEarned();

	public double sumTransactions() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}

	private boolean isWithdrawExist(double amount) {
		if (sumTransactions() < amount) {
			throw new IllegalArgumentException(
					"not sufficient fund into the account");
		}
		return true;
	}

	public int getAccountType() {
		return accountType;
	}

	public boolean isValidAmount(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		}
		return true;

	}

	public boolean hasWithdrawnInLast10Days() {
		List<Transaction> withdrawnTransaction = transactions.stream()
				.filter(t -> (t.isWithdrawl())).collect(Collectors.toList());
		for (Transaction t : withdrawnTransaction)
			if ((Calendar.getInstance().getTime().compareTo(t
					.getTransactionDate())) <= 10) {
				return true;
			}
		return false;
	}

}
