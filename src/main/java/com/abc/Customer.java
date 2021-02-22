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
		String statement = null;
		statement = "Statement for " + name + "\n";
		double total = 0.0;
		for (Account a : accounts) {
			statement += "\n" + statementForAccount(a) + "\n";
			total += a.sumTransactions();
		}
		statement += "\n Total In All Accounts " + toDollars(total);
		return statement;
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
	
	private void transferBetweenAccounts(Account accountFrom, Account accountTo,double amount) throws Exception {
		if (accountIsExist(accountFrom)&&accountIsExist(accountTo)&&amount>0) {
			if (balanceCheck(accountFrom,amount)) {
				accountFrom.withdraw(amount, accountFrom.getAccountType());
				accountTo.deposit(amount, accountTo.getAccountType());
			}
		} else {
			throw new IllegalArgumentException("amount must be greater than zero, accounts must be exists.");
		}
		
	}

	private boolean accountIsExist(Account isExist) {
		for (Account ac : accounts) 
			if (ac.getAccountType()==isExist.getAccountType())
				return true;
		return false;
	}

	private boolean balanceCheck(Account accountTypeFrom, double amount) {
		return accountTypeFrom.getBalance()>amount;
	}
}
