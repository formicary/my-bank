package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;
    private Date date;

    public Transaction(double amount) {
        this.amount = amount;
        this.date = DateProvider.getInstance().now();
    }
    
    public Date getTransactionDate() {
    	return date;
    }
    
    public double getAmount() {
    	return amount;
    }

}