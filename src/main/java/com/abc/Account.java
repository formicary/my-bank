package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    public List<Transaction> transactions = new ArrayList<Transaction>();
    protected double accountValue = 0.0;
    protected String accountType;

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

    public String getAccountType() {
        return accountType;
    }

}
