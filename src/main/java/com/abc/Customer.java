package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.abs;

import java.text.NumberFormat;

public class Customer implements Common {

	private String name;
	private List<Account> accounts;

	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	/**
	 * Open new account for customer
	 * @param account account to add
	 * @return customer with new account
	 */
	public Customer openAccount(Account account) {
		accounts.add(account);
		return this;
	}
	/**
	 * Return number of accounts held by customer
	 * @return number of accounts
	 */
	public int getNumberOfAccounts() {
		if(!accounts.isEmpty()) {
			return accounts.size();
		}
		else {
			return 0;
		}

	}
	public boolean isValidAcc(int accountNo) {
		return getAccount(accountNo) != null;
	}
	public Account getAccount(int accountNo) {
		Account account = null;
		for (Account a: accounts) {
			if (accountNo == a.getAccountNo()) {
				account = a;
			}
		}
		return account;
	}
	/**
	 * Calculate total interest earned on all accounts held by customer
	 * @return total interest earned
	 */
	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned();
		return total;
	}

	/**
	 * Prints bank statement of all accounts held by customer
	 * @return customer bank statement
	 */
	public String getStatement() {
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
	/**
	 * Displays account type as a string
	 * @param a account
	 * @return account type
	 */
	private String statementForAccount(Account a) {
		String s = "";

		if(a instanceof CheckingAccount) {
			s += "Checking Account\n";
		}
		else if(a instanceof SavingsAccount) {
			s += "Savings Account\n";
		}
		else if(a instanceof MaxiSavingsAccount) {
			s += "Maxi Savings Account\n";
		}

		//Now total up all the transactions
		double total = 0.0;
		for (Transaction t : a.getTransactions()) {
			s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
			total += t.amount;
		}
		s += "Total " + toDollars(total);
		return s;
	}
	/**
	 * Display amount in Canadian currency
	 */
	@Override
	public String toDollars(double d) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CANADA);
		String currency = nf.format(abs(d));
		return currency;		
	}

	public String getName() {
		return name;
	}    

	public List<Account> getAccounts() {
		return accounts;
	}

}
