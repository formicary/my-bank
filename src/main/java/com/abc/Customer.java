package com.abc;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;

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

	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned(a, a.getBalance());
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
		statement += "\n Total In All Accounts " + toDollars(total);
		return statement;
	}

	private String statementForAccount(Account a) {
		String type = a.getAccountType();
		String s = "";

		switch (type) {
		case "CHECKING":
			s += "Checking Account\n";
			break;
		case "SAVINGS":
			s += "Savings Account\n";
			break;
		case "MAXISAVINGS":
			s += "Maxi Savings Account\n";
			break;
		
		}

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

	public String transferBetweenAccounts(Account accountFrom, Account accountTo, double amount) {
		if (accountTypeIsExist(accountFrom) && accountTypeIsExist(accountTo) && amount > 0) {
			accountFrom.withdraw(amount, accountFrom.getAccountType());
			accountTo.deposit(amount, accountTo.getAccountType());
			return "Transfer completed";
		} else {
			return "amount must be greater than zero, accounts must be exists.";
		}

	}

	public boolean accountTypeIsExist(Account isExist) {
		if (!(isExist == null)) 
			for (Account ac : accounts)
				if (ac.getAccountType() == isExist.getAccountType())
					return true;
		return false;
	}
	

}
