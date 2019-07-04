package com.abc;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static java.lang.Math.abs;

/**
 * Customer class sets customer name and account list for the customer, adds
 * accounts for a customer, gets customer name, gets number of accounts, gets
 * total interests earned for all the accounts and produces bank statement for a
 * customer with all the transaction details for all the accounts that the
 * customer has including interest earned as per defined interest rates and
 * rules 
 * @version 2.0 03 July 2019 
 * @updated by Dhurjati Dasgupta
 */

public class Customer {
	private String customerName;
	LinkedList<Account> accounts;

	Customer(String customerName) {
		this.customerName = customerName;
		this.accounts = new LinkedList<Account>();
	}

	/* Gets customer Name */
	public String getName() {
		return customerName;

	}

	/* Adds account to the account list for a customer */
	public Customer openAccount(Account account) {
		accounts.add(account);
		return this;
	}

	/* Gets number of accounts in the account list for a customer */
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	/**
	 * Gets total interest paid for all the accounts in the account list for a
	 * customer and
	 * 
	 * @return total interest paid
	 */
	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned(a);

		return total;
	}

	/**
	 * Produces bank statement for a customer with all the transaction details for
	 * all the accounts that the customer has including interest earned as per
	 * defined interest rates and rules
	 * 
	 * @return statement
	 */
	public String getStatement() {
		String statement = null;
		String formatted = "";
		SimpleDateFormat simplerFormat = new SimpleDateFormat("dd-MMM-YYYY");
		formatted = simplerFormat.format(DateProvider.getInstance().now().getTime());

		statement = "Statement for " + customerName + "\n";
		statement = statement.concat("Statement Date:" + formatted + "\n");

		double total = 0.0;
		for (Account a : accounts) {
			statement = statement.concat("\n" + statementForAccount(a) + "\n");
			statement = statement.concat("Total Interest Earned For " + a.getAccountType(a.getAccountType()) + " "
					+ toDollars(a.interestEarned(a)));
			total += a.sumTransactions();
		}

		statement = statement.concat("\nTotal Interest Earned " + toDollars(totalInterestEarned()));

		statement = statement.concat("\nTotal Balance for All Accounts (including interests) "
				+ toDollars(total + totalInterestEarned()) + "\n\n");
		return statement;
	}

	/**
	 * Produces bank statement for an Account passed as parameter with all the
	 * transaction details that the Account has including interest earned as per
	 * defined interest rates and rules
	 * 
	 * @return statement
	 */
	private String statementForAccount(Account a) {
		String statement = "";
		SimpleDateFormat simplerFormat = new SimpleDateFormat("dd-MMM-YYYY");
		String formatted = "";

		// Translate to pretty account type
		switch (a.getAccountType()) {
		case Account.CHECKING:
			statement = statement.concat("Checking Account\n");
			break;
		case Account.SAVINGS:
			statement = statement.concat("Savings Account\n");
			break;
		case Account.MAXI_SAVINGS:
			statement = statement.concat("Maxi Savings Account\n");
			break;
		}

		double total = 0.0;

		statement = statement
				.concat(String.format("%-20s%-20s%-20s%-20s\n", "Date", "Deposit", "Withdrawal", "Balance"));

		for (Transaction t : a.transactions) {

			total += t.amount;
			formatted = simplerFormat.format(t.transactionDate.getTime());
			statement = statement.concat(String.format("%-20s", formatted)
					+ ((t.amount > 0) ? String.format("%-20s%-20s", toDollars(t.amount), " ")
							: String.format("%-20s%-20s", " ", toDollars(t.amount)))
					+ String.format("%-20s\n", toDollars(total)));
		}
		return statement;
	}

	/*
	 * Converts to dollar formatted with absolute value for the amount passed as
	 * input parameter
	 * 
	 * @return dollar formatted amount with absolute value
	 */
	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}
}
