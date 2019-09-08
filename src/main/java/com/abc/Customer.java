package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Customer {

	private String name;
	private List<Account> accounts;

	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	public Customer openAccount(Account account) {
		accounts.add(account);
		return this;
	}

	/*
	 * Getters
	 */
	public String getName() {
		return name;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned(a.getBalance(), a.getInterestRate(), 365, 1);
		return total;
	}

	public String getStatement() {
		String statement = null;
		statement = "Statement for " + name + "\n";
		double total = 0.0;
		for (Account a : accounts) {
			statement += "\n" + statementForAccount(a) + "\n";
			total += a.getBalance();
		}
		statement += "\nTotal In All Accounts " + Formatter.toDollars(total);
		return statement;
	}

	private String statementForAccount(Account a) {
		String s = "";
		// Translate to pretty account type
		if (a instanceof CheckingAccount) {
			s += "Checking Account\n";
		} else if (a instanceof SavingsAccount) {
			s += "Savings Account\n";
		} else {
			s += "Maxi Savings Account\n";
		}
		// Now total up all the transactions
		double total = 0.0;
		for (Transaction t : a.getTransactions()) {
			String x = "";
			if (t.getTransactionType() == "withdraw") {
				x = "withdrawal";
			} else {
				x = "deposit";
			}
			s += "  " + x + " " + Formatter.toDollars(t.getAmount()) + "\n";
			total = a.getBalance();
		}
		s += "Total " + Formatter.toDollars(total);
		return s;
	}
}
