package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) throws IllegalArgumentException{
    	if (amount == 0)
    	{
    		throw new IllegalArgumentException("Transaction amount cannot be zero");
    	}
        this.amount = amount;
        this.transactionDate = DateProvider.now();
    }
    public Transaction(double amount, Date transactionDate)
    {
    	this(amount);
    	this.transactionDate = transactionDate;
    }
    public Date getTransactionDate()
    {
    	return transactionDate;
    }
}