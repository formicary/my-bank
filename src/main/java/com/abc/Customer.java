package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
	private String name;
	private List<Account> accounts;

	public Customer (String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	public String getName () {
		return name;
	}

	public Account openAccount (Account account) {
		accounts.add(account);
		return account;
	}

	public int getNumberOfAccounts () {
		return accounts.size();
	}

	public double totalInterestEarned () {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned();
		return total;
	}

	public String getStatement () {
		String statement = null;
		statement = "Statement for " + name + "\n";
		double total = 0.0;
		for (Account a : accounts) {
			statement += "\n" + statementForAccount(a) + "\n";
			total += a.sumTransactions();
		}
		statement += "\nTotal In All Accounts " + toDollars(total);
		return statement;
	}

	private String statementForAccount (Account a) {
		String s = "";
		switch (a.getAccountType()) {
			case Account.CHECKING:
				s += "Checking Account\n";
				break;
			case Account.SAVINGS:
				s += "Savings Account\n";
				break;
			case Account.MAXI_SAVINGS:
				s += "Maxi Savings Account\n";
				break;
		}
		double total = 0.0;
		for (Transaction t : a.transactions) {
			s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
			total += t.amount;
		}
		s += "Total " + toDollars(total) + "\n";
		return s;
	}

	private List<Account> getAccounts () {
		return accounts;
	}

	private void accTransfer (Account acc1, Account acc2, double amount) {
		if (! accounts.contains(acc1) || ! accounts.contains(acc2)) {
			throw new IllegalArgumentException("Accounts must be owned by customer!");
		} else {
			acc1.withdraw(amount);
			acc2.deposit(amount);
		}
	}

	public static String toDollars (double d) {
		return String.format("$%,.2f", abs(d));
	}
}
