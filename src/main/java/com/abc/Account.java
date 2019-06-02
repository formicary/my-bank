package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Account {
    
    protected double balance = 0;
    
    protected List<Transaction> transactions = new ArrayList<>();

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            balance += amount;
        }
    }

    public void withdraw(double amount) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	} else {
    		transactions.add(new Transaction(-amount));
    		balance -= amount;
    	}
    }

    public abstract double interestEarned();

    public double getBalance() {
        return balance;
    }
    
    public abstract String getAccountType();
    
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

}
