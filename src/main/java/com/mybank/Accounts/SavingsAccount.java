package com.mybank.Accounts;

import com.mybank.Transaction;
import com.mybank.Utlities.AccountType;
import com.mybank.Utlities.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class SavingsAccount extends Account {
    public List<Transaction> transactions;

    public SavingsAccount() {
        super(AccountType.SAVINGS);
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, TransactionType.DEPOSIT));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (amount > sumTransactions()) {
            throw new IllegalArgumentException("Amount exceeds Account balance");
        } else {
            transactions.add(new Transaction(-amount, TransactionType.WITHDRAWAL));
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction transaction : transactions)
            amount += transaction.amount;
        return amount;
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 1000)
            return amount * 0.001;
        else
            return (amount - 1000) * 0.002;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }
}
