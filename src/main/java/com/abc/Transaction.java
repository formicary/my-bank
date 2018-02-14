package com.abc;

import java.util.Date;

/**
 * A class to represent transactions. A transaction has an amount and a date.
 * 
 * @author JT
 *
 */
public class Transaction {
	private final double amount;
	private Date transactionDate;

	/**
	 * A constructor for a Transaction object. This will set the amount and date.
	 * 
	 * @param amount
	 *            The value of the transaction.
	 */
	public Transaction(double amount) {
		this.amount = amount;
		this.transactionDate = DateProvider.getInstance().now();
	}

	/**
	 * A method to get the amount of the transaction.
	 * 
	 * @return A double value of the transaction amount.
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * A method to get the date of the transaction.
	 * 
	 * @return A Date object corresponding to the transaction date.
	 */
	public Date getTransactionDate() {
		return (Date) transactionDate.clone();
	}

}
