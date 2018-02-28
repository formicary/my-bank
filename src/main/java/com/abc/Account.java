package com.abc;

import java.util.ArrayList;
import java.util.List;

import static com.abc.TransactionType.*;

public class Account {
    private final AccountType accountType;
    private List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    private void addTransAction(Transaction t) {
        if (t != null) {
            transactions.add(t);
        } else {
            throw new IllegalArgumentException("Transaction must not be null!");
        }
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            addTransAction(new Transaction(amount, DEPOSIT));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            addTransAction(new Transaction(amount, WITHDRAW));
        }
    }

    public void interest(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must not be negative");
        } else {
            addTransAction(new Transaction(amount, INTEREST));
        }
    }

    public double getInterestEarned() {
        double interestEarned = 0.0;
        for (Transaction t : transactions) {
            if (t.getTransactionType() == INTEREST)
                interestEarned += t.getAmount();
        }
        return interestEarned;
    }

    public double getBalance() {
        double balance = 0.0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public void calculateInterest() {
        // Should be called at the end of the day
        interest(getInterest(getBalance()));
    }

    private double getInterest(double amount) {
        if (amount <= 0.0) {
            return 0.0;
        } else {
            switch (accountType) {
                case SAVINGS:
                    if (amount <= 1000)
                        return amount * 0.001;
                    else
                        return 1.0 + (amount - 1000.0) * 0.002;
                case MAXI_SAVINGS:
                    if (amount <= 1000)
                        return amount * 0.02;
                    if (amount <= 2000)
                        return 20.0 + (amount - 1000.0) * 0.05;
                    return 70.0 + (amount - 2000.0) * 0.1;
                default:
                    return amount * 0.001;
            }
        }
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}
