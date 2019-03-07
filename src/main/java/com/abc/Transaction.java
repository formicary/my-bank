package com.abc;

import java.util.Date;

import com.google.common.annotations.VisibleForTesting;

/**
 * The Class Transaction.
 */
public class Transaction {
	
    /** The transaction amount. */
    public final double amount;

    private Date transactionDate;

    /**
     * Instantiates a new transaction with date at which the transaction was performed.
     *
     * @param amount the transaction amount
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    

    @VisibleForTesting
    protected Transaction(double amount, Date transactionDate) {
    	 this.amount = amount;
         this.transactionDate = transactionDate;
    }

	/**
	 * Gets the transaction date at the date transaction was performed.
	 *
	 * @return the transaction date
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

}
