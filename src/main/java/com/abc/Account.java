package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class Account {

    private List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        deposit(amount, DateProvider.INSTANCE.now());
    }

    public void deposit(double amount, Date onDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, onDate));
        }
    }

    public void withdraw(double amount) {
        withdraw(amount, DateProvider.INSTANCE.now());
    }

    public void withdraw(double amount, Date onDate) {
    	if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
    	} else {
            transactions.add(new Transaction(-amount, onDate));
    	}
    }

    public Iterable<Transaction> getTransactions() {
        return new Iterable<Transaction>(){
            public Iterator<Transaction> iterator(){ return transactions.iterator(); }
        };
    }

    public abstract String getPrettyName();
    
    public abstract double interestEarned();

    public double sumTransactions() {
        return sumTransactionsBefore(DateProvider.INSTANCE.now());
    }

    public double sumTransactionsBefore(Date endDate) {
        double amount = 0.0;
        for (Transaction t: getTransactions()){
            if(!t.getTransactionDate().after(endDate)) {
                amount += t.amount;
            }
        }
        return amount;
    }

}
