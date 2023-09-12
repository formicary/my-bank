package com.mybank.Accounts;

import com.mybank.Utlities.AccountType;
import com.mybank.Transaction;
import com.mybank.Utlities.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class CheckingAccount extends Account {
    public List<Transaction> transactions;

    public CheckingAccount() {
        super(AccountType.CHECKING);
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
        return amount * 0.001;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }
}
