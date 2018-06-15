package com.abc;

import java.util.Date;

public class Transaction {
    private double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public Date getTransactionDate() {
    	return transactionDate;
    }
    
    public double getAmount() {
    	return amount;
    }
    
}
