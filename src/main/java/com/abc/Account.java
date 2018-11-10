package com.abc;

import java.util.ArrayList;
import java.util.List;


public class Account {

    private final AccountTypes accountType;
    private List<Transaction> transactions;
    private double balance;
    private boolean overdrawn;

    public Account(AccountTypes accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0;
        this.overdrawn = false;
    }

    public AccountTypes getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            balance += amount;
            if (balance >= 0) {
                overdrawn = false;
            }
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
            if (balance < 0) {
                overdrawn = true;
            }
        }
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case CHECKING:
                    return amount * 0.001;
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                } else {
                    return 1 + (amount - 1000) * 0.002;
                }
            case MAXI_SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.02;
                } else if (amount <= 2000) {
                    return 20 + (amount - 1000) * 0.05;
                } else {
                    return 70 + (amount - 2000) * 0.1;
                }
            default:
                return 0;   // invalid account type
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

}
