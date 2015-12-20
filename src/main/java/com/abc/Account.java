package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private double balance = 0;
    public final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0;
    }

    public Account(int accountType, int bal) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = bal;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            balance = balance + amount;
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            balance = balance - amount;
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case 0:
                break;
            case 1:
                if (amount <= 1000) {
                    return amount * 0.001;
                } else {
                    return 1 + (amount - 1000) * 0.002;
                }
            case 2:
                if (amount <= 1000) {
                    return amount * 0.02;
                }
                if (amount <= 2000) {
                    return 20 + (amount - 1000) * 0.05;
                }
                return 70 + (amount - 2000) * 0.1;
        }
        return amount * 0.001;
    }

    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.amount;
        }
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

    public List<Transaction> transactionSummary() {
        return transactions;
    }

}
