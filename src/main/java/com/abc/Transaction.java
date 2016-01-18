package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;
    public Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.now();
    }
    
    public Date getTransactionDate()
    {
    	return transactionDate;
    }
    
    
    public double getAmount()
    {
    	return amount;
    }
    
}
