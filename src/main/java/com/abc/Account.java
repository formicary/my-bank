package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
	
    public ArrayList<Transaction> transactions;
    
    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            this.transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {  
    	double totalInAccount = checkIfTransactionsExist(true);
    	if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
        
    } else if (totalInAccount < amount) {
    	throw new IllegalArgumentException("amount must be less than what is currently available in the bank");
    	
    } else {
        transactions.add(new Transaction(-amount));
    }
}

    public abstract double interestEarned(); 
    
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }
}
