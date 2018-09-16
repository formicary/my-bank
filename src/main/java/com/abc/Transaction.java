package com.abc;

import java.time.LocalDate;

/**
 * A {@link Transaction} specifies an amount that is withdrawn-from or
 * deposited-to an {@link Account} at a particular point in time.
 */
public class Transaction {
	private final double amount;
	private final LocalDate transactionDate;

	/**
	 * Constructs a new {@link Transaction}.
	 * 
	 * @param amount
	 *            The amount to withdraw-from or deposit-to an {@link Account}.
	 * @param date
	 *            The date/time at which the transaction was made.
	 */
	public Transaction(double amount, LocalDate date) {
		this.amount = amount;
		this.transactionDate = date;
	}

	/**
	 * Gets the {@link Transaction} amount.
	 * 
	 * @return See above.
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Gets the time the {@link Transaction} took place.
	 * 
	 * @return See above.
	 */
	public LocalDate getTime() {
		return transactionDate;
	}
	
	/**
	 * Returns true if it is a withdrawal otherwise false.
	 * @return See above.
	 */
	public boolean isWithdrawal() {
		return amount < 0;
	}
}
