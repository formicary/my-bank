package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final AccountType accountType;
    private double balance;
    public final List<Transaction> transactions;

    public Account(final AccountType accountType) {
        this.accountType = accountType;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public void deposit(final double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        transactions.add(new Transaction(amount));
        balance += amount;
    }
    
    public void withdraw(final double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("insufficient funds");
        }
        transactions.add(new Transaction(-amount));
        balance -= amount;
    }

    public double interestEarned() {
        return accountType.calculateInterestEarned(balance);
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
