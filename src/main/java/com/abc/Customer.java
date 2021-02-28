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
			total += a.interestEarned();
		return total;
	}

	public String getStatement() {
		String statement = "Statement for " + name + "\n";
		double total = 0.0;
		for (Account a : accounts) {
			statement += typeForAccount(a);
			statement += "\n" + statementForAccount(a) + "\n";
			total += a.sumTransactions();
		}
		statement += " Total In All Accounts " + toDollars(total);
		return statement;
	}

	public String typeForAccount(Account a) {
		String type = a.getAccountType();
		String result="";
		switch (type) {
		case "CHECKING":
			 result =" Checking Account";
			 break;
		case "SAVINGS":
			result=" Savings Account";
			break;
		case "MAXISAVINGS":
			result=" Maxi Savings Account";
			break;
		}
		return result;
	}

	public String statementForAccount(Account a) {
		String s="";
		double total = 0.0;
		for (Transaction t : a.transactions) {
			s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
			total += t.amount;
		}
		s += "Total " + toDollars(total)+"\n";
		return s;
	}

	public String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}

	public String transferBetweenAccounts(Account accountFrom, Account accountTo, double amount) {
			if (amount <= 0.0 || !accounts.contains(accountFrom)|| !accounts.contains(accountTo))
				return "amount must be greater than zero and accounts must be exists.";
			
			accountFrom.withdraw(amount);
			accountTo.deposit(amount);
			return "Transfer completed";
	}

	public List<Account> getAccounts() {
		return accounts;
	}

}
