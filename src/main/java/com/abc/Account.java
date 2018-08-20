package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } 
        else if (this.sumTransactions() < amount){ //no overdraft 
            throw new IllegalArgumentException("amount must be less than the account balance");
        }
        else {
            transactions.add(new Transaction(-amount));
        }
    }

    //abstract method implemented in the subclasses
    public abstract double interestEarned();

    public double sumTransactions() {
       double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    
    public int getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }
}
