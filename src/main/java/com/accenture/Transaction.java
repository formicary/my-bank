package com.accenture;

import java.util.Date;

public class Transaction {
    private final double amount;
    private final String transactionType;
    private final Date transactionDate;

    public Transaction(double amount, String transactionType) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Transaction(double amount, Date transactionDate, String transactionType) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
    }

    public double getAmount(){
        return this.amount;
    }

    public Date getTransactionDate(){
        return this.transactionDate;
    }

    public String getTransactionType(){
        return this.transactionType;
    }

}
