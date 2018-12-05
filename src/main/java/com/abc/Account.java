package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account implements IInterestEarnable{

    private final AccountType accountType;
    protected List<Transaction> transactions;

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
            if (sumTransactions()<amount){
                throw new IllegalArgumentException("Insufficient funds in account");
            }
            transactions.add(new Transaction(-amount));
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        if (!transactions.isEmpty()){
            for (Transaction t: transactions)
                amount += t.getAmount();
        }
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}