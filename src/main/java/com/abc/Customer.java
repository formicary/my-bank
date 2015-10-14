package main.java.com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * 
 * The Customer class - Represents a customer of a Bank
 * 
 * @author Stavros Mobile
 * 
 */
public class Customer {

	/**
	 * The name of the customer
	 */
	private String name;

	/**
	 * A list with the accounts of the customer
	 */
	private List<Account> accounts;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	/**
	 * 
	 * @return name of the customer
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * Add a new account to the list of the customer
	 * 
	 * @param account
	 * @return the customer
	 */
	public Customer openAccount(Account account) {
		accounts.add(account);
		return this;
	}

	/**
	 * 
	 * @return number of accounts
	 */
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	/**
	 * 
	 * @return total interest across accounts
	 */
	public double totalInterestEarned() {
		double total = 0.;
		for (Account a : accounts)
			total += a.interestEarned();
		return total;
	}

	/**
	 * 
	 * @return The statement for all the accounts
	 */
	public String getStatement() {
		String statement = "Statement for " + name + "\n";
		double total = 0.;
		for (Account a : accounts) {
			statement += "\n" + statementForAccount(a) + "\n";
			total += a.getBalance();
		}
		statement += "\nTotal In All Accounts " + toDollars(total);
		// System.out.println(statement);
		return statement;
	}

	/**
	 * 
	 * @param account
	 * @return statement for given account
	 */
	private String statementForAccount(Account a) {
		String message = "";
		switch (a.getAccountType()) {
		case CHECKING:
			message += "Checking Account\n";
			break;
		case SAVINGS:
			message += "Savings Account\n";
			break;
		case MAXI_SAVINGS:
			message += "Maxi-Savings Account\n";
			break;
		}

		for (int i = 0; i < a.getNumberOfTransactions(); i++) {
			message += "\t"
					+ (a.getTransaction(i).getAmount() < 0 ? "withdrawal"
							: "deposit") + " "
					+ toDollars(a.getTransaction(i).getAmount()) + "\n";
		}
		message += "Total " + toDollars(a.getBalance());

		return message;
	}

	/**
	 * Allows the customer to transfer an amount between his accounts
	 * 
	 * @param out
	 *            account
	 * @param in
	 *            account
	 * @param amount
	 *            to transfer
	 */
	public void Transfer(int out, int in, double amount) {
		if (out >= this.accounts.size() || in >= this.accounts.size()) {
			throw new IllegalArgumentException("Invalid account(s)");
		} else {
			try {
				this.accounts.get(out).withdraw(amount);
				this.accounts.get(in).deposit(amount);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to complete requested transfer");
			}
		}
	}

	/**
	 * Convert an amount to appropriate display format
	 * 
	 * @param amount
	 *            - double
	 * @return amount - string
	 */
	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}

	/**
	 * 
	 * @param i
	 * @return account
	 */
	public Account getAccount(int i) {
		return this.accounts.get(i);
	}

}