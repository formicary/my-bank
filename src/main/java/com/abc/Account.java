package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Account {

    private String uniqueID;
    private List<Transaction> transactions;

    public Account() {
        this.uniqueID = UUID.randomUUID().toString();
        this.transactions = new ArrayList<Transaction>();
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
            amount += t.amount;
        return amount;
    }
    
    public String statementForAccount() {
        StringBuilder statement = new StringBuilder();
        statement.append(getAccountType() + "\n");

        for (Transaction t : transactions) {
            statement.append("  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + Utils.toDollars(t.amount) + "\n");
        }
        statement.append("Total " + Utils.toDollars(sumTransactions()));
        return statement.toString();
    }
    
    private String getAccountType() {
        return "" + this;
    }
}
