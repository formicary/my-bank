package com.abc;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds all the {@link Customer}s of the bank.
 */
public class Bank {
	private static final String ERROR_MESSAGE_TEMPLATE_CUSTOMER_DOES_NOT_EXIST = "Customer {0} does not exist.";
	private static final String ERROR_MESSAGE_TEMPLATE_ACCOUNT_DOES_NOT_EXIST = "Account {0} does not exist.";
	private final List<Customer> customers;
	private final Map<String, List<Account>> customerNameToAccounts;
	
	/**
	 * Constructs a new {@link Bank}.
	 */
	public Bank() {
		customers = new ArrayList<Customer>();
		customerNameToAccounts = new HashMap<String, List<Account>>();
	}

	/**
	 * Adds a {@link Customer} to the {@link Bank}.
	 * 
	 * @param customer
	 *            The {@link Customer} to add.
	 */
	public Customer addCustomer(String name) {
		final List<Account> accounts = new ArrayList<Account>();
		final Customer customer = new Customer(name, accounts);
		customers.add(customer);
		customerNameToAccounts.put(customer.getName(), accounts);
		return customer;
	}

    /**
     * Opens the specified {@link Account} for this {@link Customer}.
     * @param account The account to open.
     * @param customerName name of the customer.
     * @return This {@link Customer}.
     */
    public Bank openAccount(String customerName, Account account) {
    	if (!customerNameToAccounts.containsKey(customerName)) {
    		final String message = MessageFormat.format(ERROR_MESSAGE_TEMPLATE_CUSTOMER_DOES_NOT_EXIST, customerName);
    		throw new IllegalArgumentException(message);
    	}
    	final List<Account> customerAccounts = customerNameToAccounts.get(customerName);
    	customerAccounts.add(account);
    	return this;
    }
    
	/**
	 * Creates a Summary of all {@link Customer}s' {@link Account}s.
	 * 
	 * @return Summary of all customers and their accounts.
	 */
	public String customerSummary() {
		return new BankStatement(customers).build();
	}

	/**
	 * Calculates the total interest paid by the {@link Bank} to all of the
	 * {@link Customer}s.
	 * 
	 * @return the total interest paid by the {@link Bank} to all of the
	 *         {@link Customer}s.
	 */
	public double totalInterestPaid() {
		double total = 0;
		for (Customer customer : customers) {
			total += customer.totalInterestEarned();			
		}
		return total;
	}
	
	/**
	 * Transfers money between two {@link Account}s.
	 * @param from The account to transfer from.
	 * @param to The account to transfer to.
	 * @param amount The amount of money to transfer.
	 */
	public void transferBetweenAccounts(Account from, Account to, double amount) {
		from.withdraw(amount);
		to.deposit(amount);
	}
	
	/**
	 * Transfers money between {@link Customer}'s {@link Account}s.
	 * @param customerName The customers name.
	 * @param fromAccountNumber The account number of the account to transfer from.
	 * @param toAccountNumber The account number of the account to transfer to.
	 * @param amount The amount of money to transfer.
	 */
	public void transferBetweenCustomerAccounts(String customerName, String fromAccountNumber, String toAccountNumber, double amount) {
		if (!customerNameToAccounts.containsKey(customerName)) {
    		final String message = MessageFormat.format(ERROR_MESSAGE_TEMPLATE_CUSTOMER_DOES_NOT_EXIST, customerName);
    		throw new IllegalArgumentException(message);
    	}
    	final List<Account> customerAccounts = customerNameToAccounts.get(customerName);
    	Account from = null;
    	Account to = null;
    	for (Account account : customerAccounts) {
			if(account.getAccountNumber().equals(fromAccountNumber)) {
				from = account;
			} else if(account.getAccountNumber().equals(toAccountNumber)) {
				to = account;
			}
		}
    	if (from != null && to != null) {
    		from.withdraw(amount);
    		to.deposit(amount);
    	}
    	if (from == null) {
    		final String message = MessageFormat.format(ERROR_MESSAGE_TEMPLATE_ACCOUNT_DOES_NOT_EXIST, fromAccountNumber);
    		throw new IllegalArgumentException(message);
    	}
    	if (to == null) {
    		final String message = MessageFormat.format(ERROR_MESSAGE_TEMPLATE_ACCOUNT_DOES_NOT_EXIST, toAccountNumber);
    		throw new IllegalArgumentException(message);
    	}
	}
}
