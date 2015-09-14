package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    
    // True if transaction is a deposit
    private boolean type;
    
    // Date of transaction
    private Date transactionDate;

    public Transaction(double amount, boolean type) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.type = type;
    }
    
    /**
     * Date of transaction.
     * @return Date of transaction.
     */
    public Date getTransactionDate() {
    	return transactionDate;
    }

    /**
     * Type of account
     * @return Returns boolean, true if it was a deposit. False if it was a withdrawal.
     */
    public boolean getType() {
    	return type;
    }
    
    /**
     * Transaction amount
     * @return Returns the amount of the transaction.
     */
    public double getAmount() {
    	return amount;
    }
    
    /**
     * Setting the transaction date. Mainly for testing purposes.
     * @param transactionDate New Date
     */
    public void setTransactionDate(Date transactionDate) {
    	this.transactionDate = transactionDate;
    }
}

