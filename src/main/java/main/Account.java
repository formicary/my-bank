package main;

import java.util.ArrayList;
import java.util.List;
import helper.AccountTypes;
import helper.DateProvider;
import helper.Transaction;

public class Account implements Comparable<Account> {

	private final int accountType;
	public List<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
		transactions = new ArrayList<Transaction>();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}

	public double interestEarned() {

		double amount = sumTransactions();

		switch (accountType) {

		case AccountTypes.SAVINGS:
			if (amount <= 1000)
				return amount * 0.001;
			else
				return 1 + (amount - 1000) * 0.002;

		case AccountTypes.MAXI_SAVINGS:
			if (amount <= 1000)
				return amount * 0.02;
			else if (amount <= 2000)
				return 20 + (amount - 1000) * 0.05;
			// 5% interest (no withdrawals in past 10 days), otherwise 0.1%
			else if ((DateProvider.getInstance().now().getDay()
					- transactions.get(0).getTranscationDate().getDay()) >= 10) {
				return amount * 0.001;
			} else {
				return 20 + (amount * 0.05);
			}

		default:
			return amount * 0.001;
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

	public int getAccountType() {
		return accountType;
	}

	// Checks if two accounts are equal.
	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass())
			return false;

		Account other = (Account) obj;

		return this.accountType == (other.accountType);
	}

	// Allows two accounts to be compared with each other.
	@Override
	public int compareTo(Account other) {
		return Integer.compare(this.accountType, other.accountType);
	}

	// Overriding the default toString representation of the Account class.
	@Override
	public String toString() {
		return "Account [accountType=" + accountType + ", transactions=" + transactions + "]";
	}

}
