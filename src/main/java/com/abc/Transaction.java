package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    public final double newBalance;
    private String description;

    private Date transactionDate;

    public Transaction(double amount, double newBalance) {
        this.amount = amount;
        this.newBalance = newBalance;
        this.transactionDate = DateProvider.getInstance().now();
        
        if (amount < 0.0 )	{
        	this.description = "Withdrawal";
        } else if (amount > 0.0)	{
        	this.description = "Deposit";
        } else {
        	throw new IllegalArgumentException("Transaction amount cannot be zero");
        }
    }
    
    public Date getTransactionDate()	{
    	return this.transactionDate;
    }
    
    public double getAmount()	{
    	return amount;
    }
    
    public String getDescription()	{
    	return description;
    }
    
    public String toString()	{
    	return "Transaction: " + this.description + " , amount = " + this.amount;
    }
}
