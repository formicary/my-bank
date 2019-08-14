package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {

	private Customer owner;
	public List<Transaction> transactions;

	public Account() {
		this.transactions = new ArrayList<Transaction>();
	}

	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		} else if (sumTransactions() < amount) {
			throw new IllegalArgumentException("Amount cannot be greater than your balance");

		} else {
			transactions.add(new Transaction(-amount));
		}
	}

	public void transfer(double amount, Account account) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
			// A customer can transfer between their accounts only
		} else if (account == null || account.getOwner() != this.getOwner()) {
			throw new IllegalArgumentException("Account number is incorrect");
		} else {
			this.withdraw(amount);
			account.deposit(amount);
		}
	}

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}
	
	public Transaction getLastWithdrawTransaction() {
		Date last = new Date(Long.MIN_VALUE);
		int index = -1;

		int i = 0;
		for (Transaction t : transactions) {
			if (t.amount < 0 && t.getTransactionDate().getTime() > last.getTime()) {
				last = t.getTransactionDate();
				index = i;
			}
			i++;
		}
		if (last == new Date(Long.MIN_VALUE)) {
			throw new IllegalArgumentException("This account does not have any withdraw transactions");
		} else {
			if(index == -1) return null;
			return transactions.get(index);
		}
	}

	public Date getLastWithdrawDate() {
		
		if(getLastWithdrawTransaction() != null) {
			getLastWithdrawTransaction().getTransactionDate();
		}
		return new Date(Long.MIN_VALUE);
	}

	public String getStatementForAccount() {
		StringBuilder s = new StringBuilder();

		// Translate to pretty account type
		s.append(getAccountName() + "\n");

		// Now total up all the transactions
		double total = 0.0;
		for (Transaction t : transactions) {
			s.append("  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n");
			total += t.amount;
		}
		s.append("Total " + toDollars(total));
		return s.toString();
	}
	
	// could move to different class 
	public static String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}

	public abstract double interestEarned();

	public abstract String getAccountName();

}
