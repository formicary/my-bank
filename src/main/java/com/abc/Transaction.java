package com.abc;

import java.util.Date;

public class Transaction {
    private final double amount;
    private final Date transactionDate;
    private final TransactionType transactionType;

    public Transaction(double amount, TransactionType transactionType) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType = transactionType;
    }
    
    // getter method for amount
    public double getAmount() {
    	return amount;
    }
    
    // getter method for Date
    public Date getDate() {
    	return transactionDate;
    }
    
    // getter method for type of transaction
    public TransactionType getTransactionType() {
    	return transactionType;
    }
}
