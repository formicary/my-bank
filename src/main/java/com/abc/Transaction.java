package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
    }

	/**
	 * @return Transaction value
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @return Transaction date
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

}
