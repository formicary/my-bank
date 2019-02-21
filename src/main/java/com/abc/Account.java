package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class Account {

    public List<Transaction> transactions;
    public static final int YEARLY = 1;
    public static final double DAILY = 1.0/365;
    private final UUID accountID;

    public Account(UUID accountID) {
        this.accountID = accountID;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, date));
        }
    }

    public void withdraw(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (amount > sumTransactions()) {
            throw new RuntimeException("account: " + accountID + " has insufficient funds");
        } else {
            transactions.add(new Transaction(-amount, date));
        }
    }

    public abstract double interestEarned(double turnover);

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.amount;
        return amount;
    }

    public UUID getAccountID() {
        return accountID;
    }

}
