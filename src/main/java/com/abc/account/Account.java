package com.abc.account;

import com.abc.customer.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Account {

    private final Customer customer;
    private final AccountType accountType;
    private final List<Transaction> transactions;
    private double balance;

    protected Account(Customer customer, AccountType accountType) {
        this.customer = customer;
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }

    public final void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        transactions.add(new Transaction(amount));
        balance += amount;
    }

    public final void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (balance < amount) {
            throw new IllegalArgumentException("Insufficient founds.");
        }
        transactions.add(new Transaction(-amount)); // TODO: Transaction type
        balance -= amount;
    }

    public void transfer(double amount, Account targetAccount) {
        if (targetAccount == this) {
            throw new IllegalArgumentException("Target account must be a different account.");
        }
        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account cannot be null.");
        }
        withdraw(amount);
        targetAccount.deposit(amount);
    }

    // Currently it's the same as the account balance, but I choose not to remove it.
    // Later it could be easily extended with a parameter (eg. time interval)
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

    public Transaction getLastTransaction() {
        if (transactions.isEmpty()) {
            return null;
        }
        return transactions.get(transactions.size() - 1);
    }

    public abstract double calcInterestEarned();

    public double getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}
