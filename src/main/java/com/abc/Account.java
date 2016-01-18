package com.abc;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Account {
    	
    public String  accountType;
    public double totalBalance;
    public List<Transaction> transactions;
    
    public Account(String accountType) 
    {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) 
    {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
        sumTransactions(); 
    }

    public void withdraw(double amount) {
    	if (amount <= 0) 
    	{
    		throw new IllegalArgumentException("amount must be greater than zero");
    	}
    	
    	else if(amount>totalBalance)
    	{
    		throw new IllegalArgumentException("Withdrawal amount is greater than account balance");
    	}
    	
    	else 
    	{
    		transactions.add(new Transaction(-amount));	
    	}
    	sumTransactions();
    }
 
    public Date latestWithdrawal()
    {
    	Date maxDate = null;
    	
    	for(int i=transactions.size()-1; i>=0; i--)
    	{	
    		if(transactions.get(i).getAmount()<0)
    		{
    			maxDate = transactions.get(i).getTransactionDate();
    			break;
    		}
    	}
		return maxDate;
    }
    
    public abstract double interestEarned();
    
    public void sumTransactions() 
    {
    	double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        this.totalBalance = amount;
    }

    public String getAccountType() {
        return accountType;
    }
}