package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private final Type accountType;
    private final List<Transaction> transactions;

    public Account(Type accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        transactions.add(new Transaction(amount));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater   than zero.");
        }
        if (sumTransactions() - amount < 0) {
            throw new IllegalArgumentException("Not enough money on your account.");
        }
        transactions.add(new Transaction(-amount));
    }

    public double interestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 0)
                    return 0;
                if (amount <= 1000 && amount > 0)
                    return amount * 0.001;
                else
                    return ( 1 + (amount - 1000) * 0.002);
            case MAXI_SAVINGS:
                if (amount <= 0)
                    return 0;
                if (amount <= 1000 && amount > 0)
                    return amount * 0.02;
                if (amount <= 2000 && amount > 1000)
                    return (20 + (amount - 1000) * 0.05);
                else
                    return (70 + (amount - 2000) * 0.1);

            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        double amount = 0.0;

        for (Transaction transaction : transactions) {
            amount += transaction.getAmount();
        }

        return amount;
    }

    public Type getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public static enum Type {
        CHECKING("Checking Account"),
        SAVINGS("Savings Account"),
        MAXI_SAVINGS("Maxi Savings Account");

        private String description;

        private Type(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

}
