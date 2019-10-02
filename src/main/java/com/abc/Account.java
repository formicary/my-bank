package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

	protected List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }
        
    @Override
    public abstract String toString();
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } 
        else {
            transactions.add(new Transaction(amount));
        }
    }
	
	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be greater than zero");
	    } 
	    else {
	        transactions.add(new Transaction(-amount));
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
