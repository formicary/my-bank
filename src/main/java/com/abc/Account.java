package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class Account {

	protected List<Transaction> transactions;
	protected Date lastWithdrawalDate;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
        this.lastWithdrawalDate = new GregorianCalendar(1970, 0, 0).getTime();
    }
        
    @Override
    public abstract String toString();
    
    public void deposit(double amount) {
    	deposit(amount, Calendar.getInstance().getTime());
    }
        
    public void deposit(double amount, Date transactionDate) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } 
        else {
            transactions.add(new Transaction(amount, transactionDate));
        }
    }
    
    public void withdraw(double amount) {
    	withdraw(amount, Calendar.getInstance().getTime());
    }
	
	public void withdraw(double amount, Date transactionDate) throws IllegalArgumentException {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be greater than zero");
	    } 
	    else {
	        transactions.add(new Transaction(-amount, transactionDate));
	        if (transactionDate.compareTo(lastWithdrawalDate) > 0) {
	        	lastWithdrawalDate = transactionDate;
	        }
	    }
	}
	
	public String getStatement() {
		String statement = this.toString() +"\n";
		
		for (Transaction transaction: transactions) {
			statement += "\n" + transaction.toString();
		}
		
		statement += String.format("\nTotal: $%.2f", sumTransactions());
		return statement;
	}
	
	public double sumTransactions() {
		double sum = 0.0;
		for (Transaction transaction: transactions)
			sum += transaction.getAmount();
		return sum;
	}
	
	public abstract double getInterest();
}
