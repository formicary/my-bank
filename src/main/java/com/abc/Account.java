package com.abc;

import java.util.ArrayList;
import java.util.List;

import static com.abc.Utility.toDollars;
import static java.lang.Math.abs;

public abstract class Account {
    private List<Transaction> transactions = new ArrayList<Transaction>();
    private String accountType;
    protected double accountValue;
    private String accountStatement;

    public Account(String accountType) {
        this.accountType = accountType;
        this.accountStatement = accountType + "\n";
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            accountValue += amount;
            accountStatement += String.format("  deposit " + toDollars(amount) + "\n");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            accountValue -= amount;
            accountStatement += String.format("  withdrawal " + toDollars(amount) + "\n");
        }
    }

    public String getAccountStatement() {
        return accountStatement += String.format("Total " + toDollars(accountValue));
    }

    public abstract double interestEarned();

    public double getAccountValue() {
        return accountValue;
    }
}
