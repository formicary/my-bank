package com.abc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {

	protected double balance;
	protected String accName;
	public List<Transaction> transactions = new ArrayList<Transaction>();

	public List<Transaction> getTransactions() {
		return transactions;
	}
	/**
	 * Deposit into account.
	 * @param amount The amount to be deposited.
	 */
	public synchronized void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount, true));

			balance += amount;
		}
	}

	/**
	 * Withdraw from account.
	 * @param amount The amount to be withdrawn
	 */
	public synchronized void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else if (amount>balance){
			throw new IllegalArgumentException(
					"Amount requested exceeds the balance available in account");
		}
		else {
			transactions.add(new Transaction(-amount, false));
			balance -= amount;
		}
	}

	/**
	 * Retrieves current account balance.
	 * @return Returns balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Calculates interest by going over all transactions and calculating the daily interest. Calculates the interest between all transactions, up to the current date.
	 * @return Total interest earned to date.
	 */
	public double interestEarned() {

		// Day in milliseconds
		final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
		// Current date
		Date currentDate = DateProvider.getInstance().now();
		// Sum of all Interest
		double interest = 0;
		// Balance at current time of transaction
		double prevBalance = 0;
		// Go over each transaction. Calculates the number of days apart from
		for (int i = 0; i < transactions.size(); i++) {
			// DAte of the transaction
			Date transDate = transactions.get(i).getTransactionDate();
			// Number of days between each transaction
			double diffInDays;
			// Calculates number of days between each transaction. If the current transaction is the latest one, then the current date is used.
			if (i != transactions.size() - 1) {
				diffInDays = (int) ((transactions.get(i + 1)
						.getTransactionDate().getTime() - transDate.getTime()) / DAY_IN_MILLIS);
			} else {
				diffInDays = (int) ((currentDate.getTime() - transDate
						.getTime()) / DAY_IN_MILLIS);
			}
			prevBalance += transactions.get(i).getAmount();

			if (diffInDays > 0) {
				interest += calcInterest(diffInDays, prevBalance);
			}
		}
		DecimalFormat df = new DecimalFormat("#.##");      
		interest = Double.valueOf(df.format(interest));
		return interest;
	}

	/**
	 * Abstract method which is implemented by all types of accounts to calculate the interest. Calculates the interest between two dates.
	 * @param diffInDays Number of days the interest needs to be calculated for.
	 * @param balance Balance at that time.
	 * @return Interest in that period of time.
	 */
	public abstract double calcInterest(double diffInDays, double balance);

	/**
	 * Account type.
	 * @return Returns the type of account.
	 */
	public String getAccountType() {
		return accName;
	}

}
