package com.abc;

import static java.lang.Math.abs;

//import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    private String description;
    private final int transactionType;
    private Date transactionDate;
    
    public static final int WITHDRAWAL = 0;
    public static final int DEPOSIT = 1;

    public Transaction(double amount, int transactionType) {
    	
    	this.transactionType = transactionType;
       
		this.amount = amount;
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
    	
    	switch(transactionType) {
    	case WITHDRAWAL :
    		return (-amount);
    	
    	case DEPOSIT :
    		return amount;
    	default:
    		return amount;
    	}
    }
    
    public String getDescription()	{
    	return description;
    }
    
    public String toString()	{
    	return "Transaction : " + this.description + " , amount = " + toDollars(this.amount);
    }
    
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
