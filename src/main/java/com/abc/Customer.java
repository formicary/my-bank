package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * A class to represent customer objects who have a name and a list of accounts.
 * 
 * @author JT
 *
 */
public class Customer {
	private String name;
	private List<Account> accounts;

	/**
	 * Constructor for the customer class which sets the name and initialises a list
	 * of accounts.
	 * 
	 * @param name
	 *            The String name of the customer.
	 */
	public Customer(String name) {
		this.name = new String(name);
		this.accounts = new ArrayList<Account>();
	}

	/**
	 * A method to add an account to the customers record.
	 * 
	 * @param account
	 *            The Account object to be added.
	 * @return this Customer object.
	 */
	public Customer openAccount(Account account) {
		accounts.add(account);
		return this;
	}

	/**
	 * A method to transfer money between two accounts belonging to a customer. This
	 * method checks whether the two accounts belong to the customer and whether
	 * there is enough money in the account to process the transaction.
	 * 
	 * @param from
	 *            The Account which will send the money.
	 * @param to
	 *            The Account which will recieve the money.
	 * @param amount
	 *            The amount of money to send.
	 * @return A boolean true if the transaction is successful. A boolean false if
	 *         the transaction is not successful.
	 */
	public boolean transferMoneyBetweenAccounts(Account from, Account to, double amount) {
		if (accounts.contains(from) && accounts.contains(to) && from.sumTransactions() > amount) {
			from.withdraw(amount);
			to.deposit(amount);
			return true;
		}
		return false;
	}

	/**
	 * A method to calculare the total interest earned from all customer accounts.
	 * @return
	 * A double value of the total interest earned.
	 */
	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts) {
			total += a.interestEarned();
		}
		return total;
	}

	/**
	 * A method to get a statement for a customer. This statement includes the statement for each account and the total in all accounts.
	 * @return
	 * 				A String of the statement.
	 */
	public String getStatement() {
		String statement = "";
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
	 * A method to generate the statement for an account. This statement includes information about the type of account, a record of transactions and a total balance.
	 * @param a
	 * The Account which the statement will be generated for.
	 * @return
	 * A String of the account statement.
	 */
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
		for (Transaction t : a.getTransactions()) {
			s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
			total += t.getAmount();
		}
		s += "Total " + toDollars(total);
		return s;
	}

	/**
	 * A method to convert a number into a dollar representation.
	 * @param d
	 * The double that will be converted.
	 * @return
	 * A String of the number converted into dollar format.
	 */
	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}

	/**
	 * A method to get the name of the customer.
	 * @return
	 * A String name value.
	 */
	public String getName() {
		String nameCopy = new String(name);
		return nameCopy;
	}

	/**
	 * A method to get the number of accounts belonging to the customer.
	 * @return
	 * An int value corresponding to the number of accounts belonging to the customer.
	 */
	public int getNumberOfAccounts() {
		return accounts.size();
	}
}
