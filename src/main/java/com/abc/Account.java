package com.abc;

import java.util.ArrayList;
import java.util.List;

import com.abc.accounts.*;

public abstract class Account {

    private final AccountType accountType;
    public List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Adds a new transaction to an account Transaction List.
     * @param amount money to deposit. Must be positive.
     * @throws IllegalArgumentException when amount <= 0
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Adds a new Transaction with negative amount of money to the Transaction List.
     * @param amount money to witdraw. Must be positive.
     * @throws IllegalArgumentException when amount <= 0
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }
    
    /**
     * TODO: consider how to handle negative transaction sum. Suggestions:
     * a) Do not allow overdraft in the first place
     *  (withdraw method checks if the specified amount of money exists)
     * b) return 0
     * 
     * @return amount of money earned depending on the account type
     */
    public abstract double interestEarned();

    @Override
    public abstract String toString();

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

}
