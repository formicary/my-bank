package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for creating the three types of account
 */
public abstract class Account {

    public List<Transaction> transactions;

    protected int accountType = 0;
    protected double accountBalance = 0.0;

    protected String accountTypeString;

    public Account(){
        this.transactions = new ArrayList<Transaction>();
    }

    public int getAccountType(){ return accountType; }

    public String getAccountTypeString() { return this.accountTypeString; }

    public double getAccountBalance(){ return this.accountBalance; }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("error: amount must be greater than zero");
        } else {
            this.transactions.add(new Transaction(amount));
            this.accountBalance += amount;
        }
    }

    public abstract void withdraw(double amount);

    public abstract double interestEarned();
}