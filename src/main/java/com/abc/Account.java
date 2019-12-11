package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class Account {

    private List<Transaction> transactions;
    private Date openingDate;

    public abstract int getAccountType();
    public abstract double getInterestEarned();
    
    public Account() {
        this.transactions = new ArrayList<Transaction>();
        openingDate = (new DateProvider()).now();
    }
    
    public abstract void update(long time, double amount); 
    
    public long getOpeningDate() {
    	return openingDate.getTime();
    }
    
    public List<Transaction> getTransactions()
    {
    	return transactions;
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        	Transaction t = new Transaction(amount);
            update(t.getDate().getTime(), t.amount);
            transactions.add(t);
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	    	Transaction t = new Transaction(-amount);
	        update(t.getDate().getTime(), t.amount);
	        transactions.add(t);
	    }
	}
  
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }
    
	public double interestEarned() {
		long today = (new DateProvider()).now().getTime();
		update(today, 0.0);
		return getInterestEarned();
	}
	
    
}
