package com.abc;

import java.util.ArrayList;
import java.util.List;


public class Account {

    private final int accountType;
    private List<Transaction> transactions;
    
    //An account is created specifying the type of account and with an empty list of transactions.
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }
    
    //Access type of account.
	public int getAccountType() { 
		return accountType; 
	}
	
	//Access list of transactions.
    public List<Transaction> getTransactions() {
    	return transactions;
    }

    //Deposit money into account.
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }
    
    //Withdraw money from account.
	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	    }
	}
	
	//Interest earned for an account, default interest rate is 0.1%.
    public double interestEarned() {
        double amount = sumTransactions();
        return amount * 0.001;
    }

    //Total amount of money in account. 
    public double sumTransactions() {
    	double amount = 0.0;
    	for (Transaction t: transactions)
    		amount += t.getAmount();
    	return amount; 	
    }
    
}
