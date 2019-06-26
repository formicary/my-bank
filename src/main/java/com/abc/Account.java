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

    // same process across accounts, so no need for repeated implementations via abstract class
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("error: amount must be greater than zero");
        } else {
            this.transactions.add(new Transaction(amount, Transaction.DEPOSIT));
            this.addFunds(amount);
        }
    }

    // can't overdraft from savings account but can from checking account, so different implementation needed
    public abstract void withdraw(double amount);

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