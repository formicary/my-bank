package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.math.BigDecimal;

/**
 * Class representing a customer to a bank who can hold many accounts.
 * 
 * @author Christopher J. Smith
 */
public class Customer {

	// Object sate variables
	private String name;
	private final List<Account> ACCOUNTS;

	/**
	 * Constructor to initialise a customer and set it's initial state.
	 * 
	 * @param name
	 *            is the name of the customer
	 * @throws IllegalArgumentException
	 *             if the name argument is null.
	 */
	public Customer(String name) {
		if (name == null) {
			throw new IllegalArgumentException(Customer.class + "::name cannot be null.");
		}
		this.name = name;
		// Ensure that elements can be added concurrently.
		this.ACCOUNTS = Collections.synchronizedList(new ArrayList<Account>());
	}

	/**
	 * Get the customers name.
	 * 
	 * @return Returns the customers stored name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the customer name to a new name.
	 * 
	 * @param aName
	 *            is the new name. If null it's ignored.
	 */
	public void setName(String aName) {
		if (aName != null) {
			name = aName;
		}
	}

	/**
	 * Open a new account for a customer.
	 * 
	 * @param account
	 *            is the account the customer is opening. Ignored if null.
	 * @return Returns the same Customer object so commands can be chained.
	 */
	public Customer openAccount(Account account) {
		if (account != null) {
			ACCOUNTS.add(account);
		}

		return this;
	}

	/**
	 * Get the number of accounts the customer has opened.
	 * 
	 * @return Returns the number of accounts the customer has opened.
	 */
	public int getNumberOfAccounts() {
		return ACCOUNTS.size();
	}

	/**
	 * Get a Iterator to iterate through all the accounts a customer has opened.
	 * 
	 * @return Returns a Iterator to for customer accounts.
	 */
	public Iterator<Account> getAccountIterator() {
		return ACCOUNTS.iterator();
	}

	/**
	 * Get a clone list of all Accounts a customer has opened.
	 * 
	 * @return Returns a clone list of all the customers accounts. Account
	 *         references remain the same.
	 */
	// Returns a clone so no outside objects can add to the collection.
	public List<Account> getAccountListClone() {
		return new ArrayList<Account>(ACCOUNTS);
	}

	/**
	 * Get the total annual interest a customer is owed given their current
	 * holdings.
	 * 
	 * @return Returns the total annual interest a customer is owed.
	 */
	public BigDecimal getTotalAnnualInterestPayable() {
		BigDecimal result = BigDecimal.ZERO;
		for (Account acc : ACCOUNTS) {
			result = result.add(acc.getAnnualInterest());
		}
		return result;
	}

	/**
	 * Get the total daily interest a customer is owed given their current holdings.
	 * 
	 * @return Returns the total daily interest a customer is owed.
	 */
	public BigDecimal getTotalDailyInterestPayable() {
		BigDecimal result = BigDecimal.ZERO;
		for (Account acc : ACCOUNTS) {
			result = result.add(acc.getDailyInterest());
		}
		return result;
	}

	/**
	 * Get the accounts total holdings across all accounts.
	 * 
	 * @return Returns the accounts total holdings.
	 */
	public BigDecimal getTotalAccountHoldings() {
		BigDecimal result = BigDecimal.ZERO;
		for (Account acc : ACCOUNTS)
			result = result.add(acc.getTransactionsSum());
		return result;
	}

	/**
	 * Get a printable statement about a customers accounts and it's holdings.
	 * 
	 * @return Returns a printable statement about a customers accounts.
	 */
	public String getStatement() {
		StringBuilder result = new StringBuilder();
		BigDecimal total = BigDecimal.ZERO;

		result.append("Statement for ").append(name).append('\n');
		// For each account append details about each transaction in that account.
		for (Account acc : ACCOUNTS) {
			result.append('\n').append(acc.getStatement()).append('\n');
			total = total.add(acc.getTransactionsSum());
		}
		result.append("\nTotal In All Accounts ");
		result.append(Transaction.toCurrecy(total));

		return result.toString();
	}

	/**
	 * Transfer holdings from one account to another account of which the customer
	 * owns.
	 * 
	 * @param from
	 *            is the account to take the funds from. The account must have
	 *            enough funds and belong to the customer.
	 * @param to
	 *            is the account to add the funds to. The account must belong to the
	 *            customer.
	 * @param amount
	 *            is the amount to transfer between accounts.
	 * @return Returns true if the transfer was successful.
	 */
	public boolean accountTransfer(Account from, Account to, BigDecimal amount) {
		boolean result = false;

		if (checkOwnAccounts(from, to)) {
			synchronized (from) {
				if (from.getTransactionsSum().compareTo(amount) >= 0) {
					from.withdraw(amount);
					to.deposit(amount);
					result = true;
				}
			}
		}

		return result;
	}

	/**
	 * Check that the customer owns every account in an array.
	 * 
	 * @param accounts
	 *            is a array or singular account to check if the account owns.
	 * @return Returns true if the account owns all accounts, else returns false.
	 */
	public boolean checkOwnAccounts(Account... accounts) {
		boolean result = true;

		if (accounts != null && accounts.length > 0) {
			// For each account check that the customer has a reference to that account.
			// If it has a reference set the entry to null.
			for (Account a : ACCOUNTS) {
				for (int i = 0; i < accounts.length; i++) {
					accounts[i] = accounts[i] == a ? null : accounts[i];
				}
			}

			// Check all accounts have been set to null.
			// If there is a non-null entry there must be an account not belonging to this
			// customer.
			for (Account a : accounts) {
				if (a != null) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

	@Override
	public String toString() {
		return "Accounts: " + getNumberOfAccounts() + "  Total Holdings: "
				+ Transaction.toCurrecy(getTotalAccountHoldings());
	}
}
