package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {

	public static final int CHECKING = 0;
	public static final int MAXI_SAVINGS = 1;
	public static final int SAVINGS = 2;

	protected final int accountNumber;
	protected final int accountType;

	/*
	 * balance variable reduces the overhead of continually recalculating
	 * transaction sums. BigDecimal types are used to store currency values, as
	 * they permit more accurate arithmetic operations than floating point /
	 * double representations.
	 */

	protected BigDecimal balance = BigDecimal.ZERO;

	protected List<Transaction> transactions;

	public Account(int accountType, int accountNumber) {
		this.accountType = accountType;
		/*
		 * allows the customer to transfer to/from accounts previously opened.
		 * Using the accountnumber identifier, each of a customer's accounts is
		 * uniquely identifiable from the others.
		 */
		this.accountNumber = accountNumber;
		this.transactions = new ArrayList<Transaction>();
	}

	/*
	 * Alters the input representation to BigDecimal, and adds it to the list of
	 * transactions for the account.
	 */
	public void deposit(double input) {
		BigDecimal amount = new BigDecimal(String.valueOf(input));

		try {
			if (amount.compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException(
						"Amount must be greater than zero.");
			}
		} catch (IllegalArgumentException iae) {
			System.out.println("Error - " + iae.getMessage());
			return;
		}

		balance = balance.add(amount);
		transactions.add(new Transaction(amount));

	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public int getAccountType() {
		return accountType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	abstract BigDecimal getInterestRateForBalance();

	abstract BigDecimal interestEarned();

	/*
	 * As in deposit, but with a conditional statement to see whether the
	 * account contains enough money to complete the request
	 */
	public boolean withdraw(double input) {
		BigDecimal amount = new BigDecimal(String.valueOf(input));

		try {
			if (amount.compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException(
						"Amount must be greater than zero.");
			}
		} catch (IllegalArgumentException iae) {
			System.out.println("Error - " + iae.getMessage());
			return false;
		}

		if (balance.compareTo(amount) < 0) {
			System.out
					.println("Unable to complete action: insufficient funds.\n"
							+ "You currently have $" + balance + " available.");
			return false;
		} else {
			balance = balance.subtract(amount);
			transactions.add(new Transaction(BigDecimal.ZERO.subtract(amount)));
			return true;
		}
	}
}
