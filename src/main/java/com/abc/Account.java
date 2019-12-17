package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final AccountType accountType;
    public final List<Transaction> transactions;

    public Account(final AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }

    public void deposit(final double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    //TODO: no check here that the customer has sufficient funds to withdraw the amount
    public void withdraw(final double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double interestEarned() {
        double balance = sumTransactions();
        return accountType.calculateInterestEarned(balance);
    }

    public double sumTransactions() {
        return transactions.stream().mapToDouble(t -> t.amount).sum();
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
