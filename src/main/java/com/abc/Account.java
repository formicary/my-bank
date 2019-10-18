package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public abstract class Account {
    private ArrayList<Transaction> transactions;
    private String name;
    private double balance;

    public Account(String name) {
        this.name = name;
        this.balance = 0;
        this.transactions = new ArrayList<Transaction>();
    }

    protected void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            balance += amount;
            String statement = "deposit " + toDollars(amount);
            transactions.add(new Transaction(amount,statement));
        }
    }

    protected void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            balance -= amount;
            String statement = "withdrawal " + toDollars(amount);
            transactions.add(new Transaction(-amount,statement));
        }
    }

    public abstract double interestEarned();

    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getName() {
        return name;
    }

    public String getBalanceinDollars() {
        return toDollars(balance);
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

}
