package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account implements InterestInterface {
    protected List<Transaction> transactions;
    protected double totalAmount;
    protected double defaultInterestRate;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
        totalAmount = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            totalAmount += amount;
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0 || amount > totalAmount) {
            throw new IllegalArgumentException("amount must be greater than zero and greater than totalAmount");
        } else {
            totalAmount -= amount;
            transactions.add(new Transaction(-amount));
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public abstract String toString();
}
