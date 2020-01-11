package com.abc;

import java.util.ArrayList;
import java.util.List;

import com.abc.accounts.*;

public abstract class Account {

    private final AccountType accountType;
    public List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
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

    @Override
    public abstract String toString();

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

}
