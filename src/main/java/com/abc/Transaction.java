package com.abc;

import java.util.Date;

public class Transaction {

    public final double amount;
    private Date transactionDate;
    public enum TransactionType {
    	DEPOSIT,
    	WITHDRAWAL,
    	INTEREST
    }
    
    private TransactionType transactionType; 
    
    
    public Transaction(double amount, TransactionType type) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType = type;
    }
    
    public Date getTransactionDate() {
    	return this.transactionDate;
    }
    
    public double getTransactionAmount() {
    	return this.amount;
    }

}
