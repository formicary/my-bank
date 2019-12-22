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
    
//  A function added to specific the time that the transaction was made. 
//  Used by Accounts in debugging mode only.
    public Transaction(double amount, TransactionType type, Date transactionDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionType = type;
    }
    
    public Date getTransactionDate() {
    	return this.transactionDate;
    }
    
    public double getTransactionAmount() {
    	return this.amount;
    }
    
    public TransactionType getTransactionType() {
    	return this.transactionType;
    }

}
