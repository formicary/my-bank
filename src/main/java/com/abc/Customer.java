package com.abc;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static java.lang.Math.abs;

public class Customer {
	private String customerName;
	LinkedList<Account> accounts;

	Customer(String customerName) {
		this.customerName = customerName;
		this.accounts = new LinkedList<Account>();
	}

	public String getName() {
		return customerName;

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
			total += a.interestEarned(a);
		return total;
	}

	public String getStatement() {
		/*
		 * Produces string which is the customers full statement including
		 * interest earned for each account
		 */
		String statement = null;
		String formatted = "";
		SimpleDateFormat simplerFormat = new SimpleDateFormat("dd-MMM-YYYY");
		formatted = simplerFormat.format(DateProvider.getInstance().now()
				.getTime());

		statement = "Statement for " + customerName + "\n";
		statement = statement.concat("Statement Date:" + formatted + "\n");

		double total = 0.0;
		for (Account a : accounts) {
			statement = statement.concat("\n" + statementForAccount(a) + "\n");
			statement = statement.concat("Total Interest Earned For "
					+ a.getAccountType(a.getAccountType()) + " "
					+ toDollars(a.interestEarned(a)));
			total += a.sumTransactions();
		}

		statement = statement.concat("\nTotal Interest Earned "
				+ toDollars(totalInterestEarned()));

		statement = statement
				.concat("\nTotal Balance for All Accounts (including interests) "
						+ toDollars(total + totalInterestEarned()) + "\n\n");
		return statement;
	}

	private String statementForAccount(Account a) {
		/*
		 * Produces string which is a statement for the account specified by the
		 * argument
		 */
		String statement = "";
		SimpleDateFormat simplerFormat = new SimpleDateFormat("dd-MMM-YYYY");
		String formatted = "";

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

		statement = statement.concat(String.format("%-20s%-20s%-20s%-20s\n",
				"Date", "Deposit", "Withdrawal", "Balance"));

		for (Transaction t : a.transactions) {

			total += t.amount;
			formatted = simplerFormat.format(t.transactionDate.getTime());
			statement = statement.concat(String.format("%-20s", formatted)
					+ ((t.amount > 0) ? String.format("%-20s%-20s",
							toDollars(t.amount), " ") : String.format(
							"%-20s%-20s", " ", toDollars(t.amount)))
					+ String.format("%-20s\n", toDollars(total)));
		}
		return statement;
	}

	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}
}
