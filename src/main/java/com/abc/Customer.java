package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
	private String name;
	private List<Account> accounts;

	public Customer(String name) {
		if (name.matches("[a-zA-Z]+") && name.length() > 2) {
			this.name = name;
			this.accounts = new ArrayList<Account>();
		} else {
			throw new IllegalArgumentException("Name must be made of only letters and be longer than 2");
		}
	}

	public String getName() {
		return name;
	}

	public Customer openAccount(Account account) {
		accounts.add(account);
		return this;
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned();
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
		statement += "\nTotal In All Accounts " + toDollars(total);
		return statement;
	}

	private String statementForAccount(Account a) {
		String s = "";

		// Translate to pretty account type
		s += a.getAccountType() + " Account\n";

		// Now total up all the transactions
		double total = 0.0;
		for (Transaction t : a.transactions) {
			s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
			total += t.amount;
		}
		s += "Total " + toDollars(total);
		return s;
	}

	public void transfer(int fromAcc, int toAcc, double amount) {
		Account from = accounts.get(fromAcc - 1);
		Account to = accounts.get(toAcc - 1);
		from.withdraw(amount);
		to.deposit(amount);
	}

	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}

}
