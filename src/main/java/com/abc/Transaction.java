package com.abc;

import java.util.Date;

/* Written by Tunc Demircan */
public class Transaction {

    private final String transactionDate;
    private final double amount;
    private final String type;

    // amount must always be greater than zero.
    public Transaction(double amount, String type) {
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        this.amount = amount;
        this.type = type;
        this.transactionDate = new Date().toString();

    }

    //Getters

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getTransactionDate() {
        return transactionDate;
    }
}
