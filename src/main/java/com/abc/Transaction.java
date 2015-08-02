package com.abc;

import java.util.Date;

public class Transaction {
	public static final int DEPOSIT = 0;
    public static final int WITHDRAW = 1;
    public static final int INTEREST = 2;
	
	private final double amount;
    private final Date transactionDate;
    private final int transactionType;
    
    public Transaction(double amount, int transactionType) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = DateProvider.getInstance().now();  
    }

	// return date of transaction
    public Date getTransactionDate() {
    	return transactionDate;
    }
    
    // return type of transaction
    public int getTransactionType() {
    	return transactionType;
    }
    
    // return transaction amount
    public double getTransactionAmount() {
    	return amount;
    }

}
