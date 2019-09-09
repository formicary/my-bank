package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Customer {

	/**
	 * String Customers have a name
	 * 
	 * List<Account> List of accounts that they own
	 */
	private String name;
	private List<Account> accounts;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name of the customer
	 */
	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	public void openAccount(Account account) {
		accounts.add(account);
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

	/**
	 * Calculates the total interest earned of all accounts
	 * 
	 * @return double sum of interest earned by all accounts
	 */
	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned(a.getBalance(), a.getInterestRate(), 365, 1);
		return total;
	}

	public String toString() {
		return "Customer name: " + name;
	}

	/**
	 * Prints a statement that shows transactions and totals of the customer's accounts
	 * 
	 * @return String representation of a bank statement
	 */
	public String printStatement() {
		String statement = "Statement for " + name + "\n";
		double total = 0.0;
		for (Account a : accounts) {
			statement += "\n" + statementForAccount(a) + statementForTransactions(a) + "\n";
			total += a.getBalance();
		}
		statement += "\nTotal In All Accounts " + Formatter.toDollars(total);
		return statement;
	}

	private String statementForAccount(Account a) {
		String s = "";
		if (a instanceof CheckingAccount) {
			s += "Checking Account\n";
		} else if (a instanceof SavingsAccount) {
			s += "Savings Account\n";
		} else {
			s += "Maxi Savings Account\n";
		}
		return s;
	}

	private String statementForTransactions(Account a) {
		String s = "";
		double total = 0.0;
		for (Transaction t : a.getTransactions()) {
			String x = "";
			if (t.getTransactionType() == "withdraw") {
				x = "withdrawal";
				total -= t.getAmount();
			} else {
				x = "deposit";
				total += t.getAmount();
			}
			s += "  " + x + " " + Formatter.toDollars(t.getAmount()) + "\n";
		}
		s += "Total " + Formatter.toDollars(total);
		return s;
	}
}
