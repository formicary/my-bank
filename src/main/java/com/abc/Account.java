package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    
    protected List<Transaction> transactions;

    public Account() {
        transactions = new ArrayList<Transaction>();
    }

    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public synchronized void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public synchronized double interestEarned() {
        double amount = sumTransactions();
        return computeInterest(amount);
    }
    
    protected abstract double computeInterest(double amount);
    
    public synchronized double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }
    
    public String statementForAccount() {
        StringBuilder statement = new StringBuilder();
        statement.append(getAccountType() + "\n");

        for (Transaction t : transactions) {
            statement.append("  " + t.getTransactionType() + " " + Utils.toDollars(t.getAmount()) + "\n");
        }
        
        statement.append("Total " + Utils.toDollars(sumTransactions()));
        return statement.toString();
    }
    
    private String getAccountType() {
        return "" + this;
    }
}
