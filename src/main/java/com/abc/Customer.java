package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.text.NumberFormat;

/**
 * @author michal.szeman
 *
 */
public class Customer {
	private String name;
	private List<Account> accounts;

	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}


	/**
	 * @param account
	 * @return
	 */
	public void openAccount(Account account) {
		accounts.add(account);
		
	}

	/**
	 * @return
	 */
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public double totalInterestEarned() {
		double total = 0.0;
		for (Account a : accounts)
			total += a.computeInterestEarned();
		return total;
	}

	/**
	 * @return
	 */
	public String getStatement() {
		StringBuilder statement = new StringBuilder();
		NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
		
		statement.append("Statement for " + name + "\n");
		double total = 0.0;
		for (Account a : accounts) {
			statement.append("\n").append(a.getStatement()).append("\n");
			total += a.getSumMoney();
		}
		statement.append("\n").append("Total In All Accounts ").append(us.format(total));
		
		return statement.toString();
	}
	
	
	
	public String getName() {
		return name;
	}
}
