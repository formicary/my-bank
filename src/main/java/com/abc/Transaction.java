package com.abc;

import java.util.Date;

/**
 * A {@link Transaction} specifies an amount that is withdrawn-from or
 * deposited-to an {@link Account} at a particular point in time.
 */
public class Transaction {
	private final double amount;
	private final Date transactionDate;

	/**
	 * Constructs a new {@link Transaction}.
	 * 
	 * @param amount
	 *            The amount to withdraw-from or deposit-to an {@link Account}.
	 */
	public Transaction(double amount) {
		this.amount = amount;
		this.transactionDate = DateProvider.getInstance().now();
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
	public Date getTransactionDate() {
		return transactionDate;
	}
}
