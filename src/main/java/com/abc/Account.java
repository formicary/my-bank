package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
    public enum AccountType {
        CHECKING,
        SAVINGS,
        MAXI_SAVINGS
    }

    private final AccountType accountType;
    private List<Transaction> transactions;

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
            if (calculateBalance() < amount) {
                throw new IllegalArgumentException("not enough money in account for withdrawal of that size");
            } else {
                transactions.add(new Transaction(-amount));
            }
        }
    }

    public double interestEarned() {
        double balance = calculateBalance();
        switch(accountType){
            case SAVINGS:
                if (balance <= 1000) {
                    return balance * 0.001;
                } else {
                    return 1 + (balance-1000) * 0.002;
                }

            case MAXI_SAVINGS:
                if (balance <= 1000) {
                    return balance * 0.02;
                } else if (balance <=2000) {
                    return 20 + (balance-1000) * 0.05;
                } else {
                    return 70 + (balance-2000) * 0.1;
                }
                
            default:
                return balance * 0.001;
        }
    }

    public double calculateBalance() {
        double balance = 0.0;
        for (Transaction t: transactions)
            balance += t.amount;
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
