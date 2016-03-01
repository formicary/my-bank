package com.abc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Customer class represent each customer of the bank
 * 
 * @author Others and Fei
 * 
 */
public class Customer {
	private String name;
	private List<Account> accounts;

	/**
	 * Customer has a name and accounts
	 * 
	 * @param name
	 */
	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	public String getName() {
		return name;
	}

	/**
	 * For user to open one type of account that he/she has not open yet.
	 * 
	 * @param account
	 * @return Customer
	 */
	public Customer openAccount(Account account) {
		// Check if user has opened the account before
		// Each customer can only have only one account of each type
		for (Account a : accounts) {
			if (account.getAccountName().equals(a.getAccountName())) {
				//Tell user they have already opened the account
				//If there are other ways to show user messages, change here
				System.out.println("You have already opened a "
						+ account.getAccountName());
				return this;
			}
		}
		accounts.add(account);
		//Tell user they successfully opened the account
		//If there are other ways to show user messages, change here
		System.out.println("You have successfully opened a "
				+ account.getAccountName() + ".");
		return this;
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}

	/**
	 * This method calculate the interest earned by the user since the first
	 * successful transaction. It will calculate all the opened account.
	 * 
	 * @return double The total interest
	 */
	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts) {
			total += a.interestEarned();
		}
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
		return Double.valueOf(twoDecimals.format(total));
	}

	/**
	 * Get the account with the specific name. The name is not case sensitive.
	 * 
	 * @param accountName
	 * @return Account
	 */
	public Account getAccount(String accountName) {
		for (Account a : accounts) {
			if (a.getAccountName().equalsIgnoreCase(accountName)) {
				return a;
			}
		}
		//Tell user they haven't opened the account
		//If there are other ways to show user messages, change here
		System.out.println("You haven't open the " + accountName + ".");
		return null;
	}

	/**
	 * Get the bank statement for user of all accounts.
	 * 
	 * @return String user bank statement
	 */
	public String getStatement() {
		// StringBuffer used for better performance
		StringBuffer str = new StringBuffer();
		str.append("Statement for " + this.name + "\n");
		double total = 0.0;
		for (Account a : accounts) {
			str.append("\n" + statementForAccount(a) + "\n");
			total += a.getBalance();
		}
		str.append("\nTotal In All Accounts " + toDollars(total));
		return str.toString();
	}

	/**
	 * This method gets the bank statement for the input account.
	 * 
	 * @param a Account Each account of user
	 * @return String Statement for the input account
	 */
	public String statementForAccount(Account a) {
		StringBuilder str = new StringBuilder();
		str.append(a.getAccountName() + "\n");
		double total = 0.0;
		//Iterate through the transactions to generate statement.
		for (Transaction t : a.getTransactions()) {
			str.append("  " + t.getType() + " " + toDollars(t.getAmount())
					+ "\n");
			total += t.getAmount();
		}
		str.append("Total " + toDollars(total));
		return str.toString();
	}

	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}
}
