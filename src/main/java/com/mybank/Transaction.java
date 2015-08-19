package com.mybank;

import java.util.Date;

public class Transaction {
    private final double transactionAmount;

    private final String transactionType;

    private Date transactionDate;

    public Transaction(double transactionAmount) {
        this.transactionAmount = transactionAmount;
        this.transactionDate = DateProvider.getInstance().now();
        if (transactionAmount < 0)
        	this.transactionType = "Withdrawal";
        else
        	this.transactionType = "Deposit";        	
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
