package com.abc;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Customer {
	private static String currencyFormat(BigDecimal n) {
		return NumberFormat.getCurrencyInstance(Locale.US).format(n.abs());
	}
	private List<Account> accounts;
	private int id;

	private String name;

	public Customer(String name, int id) {
		this.name = name;
		this.id = id;
		this.accounts = new ArrayList<Account>();
	}

	private Account checkExistence(int accnum) {
		for (Account a : accounts) {
			if (a.getAccountNumber() == accnum) {
				return a;
			}
		}
		return null;
	}

	/**
	 * The deposit and withdraw methods in Customer allow an amount to be
	 * transacted via any of the customer's accounts. This is implemented via an
	 * accountNumber for each Account - if the account number of the target is
	 * not found in the user's list of accounts (i.e. does not exist) then the
	 * method fails.
	 */
	public boolean deposit(double amount, int accountnumber) {
		Account destination = null;
		for (Account a : accounts) {
			if (a.getAccountNumber() == accountnumber) {
				destination = a;
				break;
			}
		}
		// if the account was not found in the user's list of accounts
		if (destination == null) {
			System.err.println("ERROR: account not found");
			return false;
		}

		destination.deposit(amount);
		return true;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public String getStatement() {
		String statement = null;
		statement = "Statement for " + name + "\n";
		BigDecimal total = BigDecimal.ZERO;
		for (Account a : accounts) {
			statement += "\n" + statementForAccount(a) + "\n";
			total = total.add(a.balance);
		}
		statement += "\nTotal In All Accounts " + currencyFormat(total);
		return statement;
	}

	/**
	 * 
	 * @param account
	 *            the account to be opened - must have an identifier unique from
	 *            all other accounts the customer has already opened.
	 * @return the Customer object, either as is, or with a newly opened
	 *         account.
	 */
	public Customer openAccount(Account account) {
		for (Account a : accounts) {
			if (a.getAccountNumber() == account.getAccountNumber()) {
				System.err.println("Error - account number already in use.");
				return this;
			}
		}
		accounts.add(account);
		return this;
	}

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
		default:
			System.err.println("Error - account type not valid.");
		}

		BigDecimal total = BigDecimal.ZERO;
		for (Transaction t : a.transactions) {
			s += "  "
					+ (t.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "withdrawal"
							: "deposit") + " " + currencyFormat(t.getAmount())
					+ "\n";
			total = total.add(t.getAmount());
		}

		s += "Total " + currencyFormat(total);
		return s;
	}

	public BigDecimal totalInterestEarned() {
		BigDecimal total = BigDecimal.ZERO;
		for (Account a : accounts)
			total = total.add(a.interestEarned());
		return total;
	}

	/**
	 * Attempts a withdrawal from the source account. If successful, deposits
	 * the amount into the destination account.
	 * 
	 * @param src
	 *            the account number of the source account
	 * @param dest
	 *            the account number of the destination account
	 * @param amount
	 *            the amount to transfer
	 */
	public void transfer(int src, int dest, double amount) {
		Account source = checkExistence(src);
		Account destination = checkExistence(dest);

		if (source == null || destination == null) {
			System.err.println("Account not found. Please try again.");
			return;
		}

		if (source.withdraw(amount)) {
			destination.deposit(amount);
		}
	}

	/**
	 * @param amount
	 *            the amount to withdraw
	 * @param accountnumber
	 *            the identifier for the account from which to withdraw 'amount'
	 * @return false if the account does not exist or if it contains insuffient
	 *         funds to complete the transaction, true otherwise.
	 */
	public boolean withdraw(double amount, int accountnumber) {
		Account destination = checkExistence(accountnumber);
		if (destination == null) {
			System.err.println("ERROR: account not found");
			return false;
		}
		try {
			return destination.withdraw(amount);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			System.err.println(e.getMessage());
			return false;
		}
	}

}
