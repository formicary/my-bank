package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * The Customer class represents a Customer, that has a name, and
 * can have multiple accounts.
 *
 * @author  Matthew Mukalere
 * @version 0.5
 */
public class Customer {
	private String name;
	private List<Account> accounts;

	/**
	 * Customer Class constructor.
	 * @param name Name of customer
	 */
	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	/**
	 * Gets the customer name
	 * @return name The customer name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the customer name
	 * @param account The account to open
	 * @return Customer This customer
	 */
	public Customer openAccount(Account account) {
		accounts.add(account);
		return this;
	}

	/**
	 * Gets the amount of accounts owned
	 * @return size The amount of accounts owned
	 */
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	/**
	 * Gets the total interest paid
	 * @return total The total interest paid
	 */
	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned();
		return total;
	}

	/**
	 * Gets the customer's bank statement.
	 * @return statement The bank statement
	 */
	public String getStatement() {
		String statement = null;
		statement = "Statement for " + name + "\n";
		double total = 0.0;
		for (Account a : accounts) {
			statement += "\n" + getTotalAccountTransactions(a) + "\n";
			total += a.sumTransactions();
		}
		statement += "\nTotal In All Accounts " + toDollars(total);
		return statement;
	}

	/**
	 * Gets total account transactions
	 * @param a The account
	 * @return s The total account transactions
	 */
	private String getTotalAccountTransactions(Account a) {
		String s = "";
		
		try {
			s += getAccountName(a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		double total = 0.0;
		for (Transaction t : a.transactions) {
			s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
			total += t.amount;
		}
		s += "Total " + toDollars(total);
		return s;
	}

	/**
	 * 
	 * @param a The account
	 * @return accountType Returns account type
	 * @throws Exception Invalid Account Type exception 
	 */
	private String getAccountName(Account a) throws Exception {
		switch(a.getAccountType()){
		case Account.CHECKING:
			return "Checking Account\n";
		case Account.SAVINGS:
			return "Savings Account\n";
		case Account.MAXI_SAVINGS:
			return "Maxi Savings Account\n";
		}
		throw new Exception("Invalid Account Type");
	}

	/**
	 * 
	 * @param d Dollar amount
	 * @return dollars Formatted Dollar amount with dollar symbol
	 */
	private String toDollars(double d){
		return String.format("$%,.2f", abs(d));
	}
}
