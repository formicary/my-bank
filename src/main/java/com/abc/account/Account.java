package com.abc.account;

import com.abc.branch.Customer;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    private Customer owner; // an account must belong to a customer
    private double balance; // holds the current balance
    private List<Transaction> transactions; // holds all transaction made under this account

    // An account must be opened an account holder (owner) and an opening balance
    public Account(Customer owner, double openingBalance) {
        this.owner = owner;
        try {
            this.deposit(openingBalance);
        } catch (ZeroAmountException e) {
            e.printStackTrace();
            System.err.println("Opening balance of account must be greater than zero!");
        }
        this.transactions = new ArrayList<Transaction>();
    }

    abstract public double interestEarned();
    abstract public AccountType getAccountType();

    /**
     * Add given amount of money to this account.
     * @param amount
     * @throws ZeroAmountException
     */
    public void deposit(double amount) throws ZeroAmountException {
        if (amount <= 0) {
            throw new ZeroAmountException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            this.updateBalance();
        }
    }

    /**
     * Subtract given amount of money from this account.
     * @param amount
     * @throws ZeroAmountException
     */
    public void withdraw(double amount) throws ZeroAmountException {
        if (amount <= 0) {
            throw new ZeroAmountException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            this.updateBalance();
        }
    }

    /**
     * Computes sum total of all transactions under this account
     */
    private void updateBalance() {
        double bal = 0.0;
        for (Transaction transaction : transactions) {
            bal += transaction.amount;
        }
        this.balance = bal;
    }

    public Customer getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
