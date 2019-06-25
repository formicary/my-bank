package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class AccountTemp {

    public List<Transaction> transactions;

    protected int accountType = 0;
    protected double accountBalance = 0.0;

    public AccountTemp(){
        this.transactions = new ArrayList<Transaction>();
    }

    public double getAccountBalance(){ return accountBalance; }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            accountBalance += amount;
        }
    }

    public abstract void withdraw(double amount);

}
