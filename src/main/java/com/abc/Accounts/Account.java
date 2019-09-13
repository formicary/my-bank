package com.abc.Account;

import com.abc.Transaction;

import java.util.ArrayList;
import java.util.List;

abstract public class Account {
    private List<Transaction> transactions;

    Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public abstract String getName();

    public void processTransaction(Transaction t){
        double balance = calculateBalance();
        if (balance + t.getAmount() < 0) throw E
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

    double calculateBalance() {
       return checkIfTransactionsExist(true);
    }

    double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }
}
