package com.abc.implementation;

import org.joda.time.LocalDate;

import com.abc.interfaces.ITransaction;
import com.abc.utilities.DateProvider;

public class Transaction implements ITransaction{
    private final double amount;
    private ITransaction nextTransaction;

    private LocalDate transactionDate;

    public Transaction(double amount) 
    {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public Transaction(double amount, LocalDate transactionDate) 
    {
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

	public double getAmount() 
	{
		return amount;
	}

	public LocalDate getTransactionDate() 
	{
		return transactionDate;
	}
	
	public ITransaction getNextTransaction()
	{
		return nextTransaction;
	}
	
	public void setNextTransaction(ITransaction transaction)
	{
		this.nextTransaction = transaction;
	}

}
