package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    //constructor
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    //method that retrieves the date of the transaction
    public Date getDate() {
        return transactionDate;
    }
    
    //method that retrieves the amount of the transaction
    public double getAmount() {
        return amount;
    }
}
