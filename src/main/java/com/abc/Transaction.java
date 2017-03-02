package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    /**
     * Constructor for the class
     * @param amount
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    // Getter Methods
    public Date getTransactionDate() {
		return transactionDate;
	}

	public double getAmount() {
		return amount;
	}

}
