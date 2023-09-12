package com.mybank.Accounts;

import com.mybank.Transaction;
import com.mybank.Utlities.AccountType;
import com.mybank.Utlities.TransactionType;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private final AccountType accountType;

    public Account(AccountType accountType) {
        this.accountType = accountType;
    }

    public abstract double interestEarned();

    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    public abstract List<Transaction> getTransactions();

    public abstract double sumTransactions();

    public AccountType getAccountType() {
        return accountType;
    }
}
