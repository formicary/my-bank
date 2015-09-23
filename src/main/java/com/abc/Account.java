package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public abstract class Account {

    protected String accountName;
    protected double balance;

    public List<Transaction> transactions = new ArrayList<Transaction>();

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
        } else if (amount > balance) {
            throw new IllegalArgumentException("amount is more than balance in account");
        } else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
        }
    }

    public double getBalance() {
        return balance;
    }

    public abstract double interestEarned();

    public String getAccountType() {
        return accountName;
    }

}