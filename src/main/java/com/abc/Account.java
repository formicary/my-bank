package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    public List<Transaction> transactions = new ArrayList<Transaction>();
    protected double accountValue = 0.0;

    public Account() {
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            accountValue += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            accountValue -= amount;
        }
    }

    public abstract double interestEarned();

    public double getAccountValue() {
        return accountValue;
    }

    public abstract String getAccountType();

}
