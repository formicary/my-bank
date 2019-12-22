package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
	private String name;
	private List<Account> accounts;

	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
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

//  Gets a specific account by its 'ID' to the customer.
//  This ID is the number of which it was opened. So their first account has the ID 1.
	public Account getAccountByID(int i) {
		if (i < 1) {
			throw new IllegalArgumentException("ID must be 1 or greater");
		} else if (i > accounts.size() + 1) {
//  		Return an account not found error message instead of saying out of bounds.
//  		This means that knowledge of the number of accounts is not leaked.
			throw new IllegalArgumentException("Account with this ID can't be found.");
		}
		return accounts.get(i - 1);
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
	
	public void transferBetweenAccounts(int source, int destination, double amount) {
		getAccountByID(source).withdraw(amount);
		getAccountByID(destination).deposit(amount);
				
	}

	private String statementForAccount(Account a) {
		String s = "";

		// Translate to pretty account type
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

		// Now total up all the transactions
		double total = 0.0;
		for (Transaction t : a.transactions) {
			s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
			total += t.amount;
		}
		s += "Total " + toDollars(total);
		return s;
	}

	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}
	

}
