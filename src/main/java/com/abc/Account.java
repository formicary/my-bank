package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for creating the three types of account
 */
public abstract class Account {

    public List<Transaction> transactions;

    protected int accountType = 0;
    private double accountBalance = 0.0;

    protected String accountTypeString;

    public Account(){
        this.transactions = new ArrayList<Transaction>();
    }

    // public int getAccountType(){ return accountType; }

    public String getAccountTypeString() { return this.accountTypeString; }

    public double getAccountBalance(){ return this.accountBalance; }

    // same process across accounts, so no need for repeated implementations via abstract class
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("error: amount must be greater than zero");
        } else {
            this.transactions.add(new Transaction(amount, Transaction.DEPOSIT));
            this.addFunds(amount);
        }
    }

    // same process across accounts, so no need for repeated implementations via abstract class
    // can't go into overdraft on any account (even if in reality you can) for simplicity reasons
    public void withdraw(double amount) {

        if (amount <= 0.0) {

            throw new IllegalArgumentException("error: amount must be greater than zero");

        } else if(!this.hasSufficientFunds(amount)){

            throw new IllegalArgumentException("error: insufficient funds for withdrawal");

        } else {

            transactions.add(new Transaction(-amount, Transaction.WITHDRAWAL));
            this.deductFunds(amount);
        }
    }
    // interest rate calculation is dependent on the type of account
    public abstract double interestEarned();

    public boolean hasSufficientFunds(double amount){
        return (this.accountBalance - amount >= 0.00);
    }

    public void addFunds(double amount){
        this.accountBalance += amount;
    }

    public void deductFunds(double amount){
        this.accountBalance -= amount;
    }
}