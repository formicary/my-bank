package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    private final Customer customer;
    private final AccountType accountType;
    public List<Transaction> transactions;
    private double balance;

    public Account(Customer customer, AccountType accountType) {
        this.balance = 0.0;
        this.customer = customer;
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }

    public final void deposit(double amount) {
        validateDeposit(amount);
        transactions.add(new Transaction(amount));
        balance += amount;
    }

    private void validateDeposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }

    public final void withdraw(double amount) {
        validateWithdraw(amount);
        transactions.add(new Transaction(-amount));
        balance -= amount;
    }

    private void validateWithdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (balance < amount) {
            throw new RuntimeException("Amount must be greater than balance."); // Could be a custom exception.
        }
    }

    public void transfer(double amount, Account targetAccount) {
        validateTransfer(amount, targetAccount);
        withdraw(amount);
        targetAccount.deposit(amount);
    }

    private void validateTransfer(double amount, Account targetAccount) {
        if (targetAccount == this) {
            throw new RuntimeException("Target account must be a different account.");
        }
        if (targetAccount == null) {
            throw new NullPointerException("Target account cannot be null.");
        }
        validateWithdraw(amount);
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
        return transactions.get(transactions.size()-1);
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

}
