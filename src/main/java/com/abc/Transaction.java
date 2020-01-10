package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;
    public final double newBalance;

    private Date transactionDate;
    private DateProvider dateProvider;

    public Transaction(double amount, double newBalance) {
        this.amount = amount;
        this.newBalance = newBalance;
        this.dateProvider = new DateProvider();
        this.transactionDate = dateProvider.now();
    }
    
    public DateProvider getDateProvider() {
    	return dateProvider;
    }
    
    public Date getTransactionDate() {
    	return this.transactionDate;
    }
}
