package com.abc;

import java.util.Date;

/**
 * Transaction class sets the amount and transaction date
 * 
 * @version 2.0 03 July 2019
 * @updated by Dhurjati Dasgupta
 */

public class Transaction {
	public final double amount;

	public Date transactionDate;

	/**
	 * sets the transaction amount based on amount passed as input parameter and
	 * sets the transaction date
	 */
	public Transaction(double amount) {
		this.amount = amount;
		this.transactionDate = DateProvider.getInstance().now();

	}

}
