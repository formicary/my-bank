package com.abc.accounts;

import com.abc.Transaction;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    private List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public abstract double interestEarned();

    //TODO Try implementing ToString here instead

    public double sumTransactions() {
        return transactions.stream().mapToDouble(i -> i.amount).sum();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
