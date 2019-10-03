package com.abc.accounts;

import com.abc.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

public abstract class Account {

    protected List<Transaction> transactions;
    protected double balance;
    protected double intRate;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0.0;
        this.intRate = 0.0;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getBalance() {
        return balance;
    }

    public double getIntRate() {
        return intRate;
    }

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
            throw new IllegalArgumentException("not enough money to cover amount");
        } else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
        }
    }

    public abstract double interestEarned();

    // Return the number of days between 2 dates
    public int daysBetween(Date start, Date end) {
        long msLength = end.getTime() - start.getTime();
        long daysLength = msLength / (1000 * 60 * 60 * 24);
        return (int) daysLength;
    }

    // Manually add transactions, to aid in testing interest calculations
    public Account addTransaction(Transaction transaction) {
        transactions.add(transaction);
        balance += transaction.getAmount();
        return this;
    }

    public String toString() {
        return "Account";
    }

    // Get statement for account
    public String getStatement() {
        String statement = this.toString();
        for (Transaction transaction : transactions) {
            statement += "\n" + transaction.toString();
        }
        statement += "\nTotal: " + String.format("$%,.2f", abs(balance));
        return statement;
    }
}