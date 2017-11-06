package com.abc;

import java.util.Calendar;
//import java.util.Date;

public class Transaction {
    private final double amount;
    private Calendar transactionDate;

    //constructor for a transaction
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    //get amount
    public double getAmount() {
    	return amount;
    }
    //get date
    public Calendar getTransactionDate() {
    	return transactionDate;
    }    
}
