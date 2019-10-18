package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private double balance;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if(balance<amount) {
            throw new IllegalArgumentException("amount must be less than the current balance");
        } else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
        }
    }

    public double interestEarned() {
        switch(accountType){
            case SAVINGS:
                if (balance <= 1000)
                    return balance * 0.001;
                return 1 + (balance-1000) * 0.002;
            case MAXI_SAVINGS:
                if (balance <= 1000)
                    return balance * 0.02;
                if (balance <= 2000)
                    return 20 + (balance-1000) * 0.05;
                return 70 + (balance-2000) * 0.1;
            default:
                return balance * 0.001;
        }
    }

    public double getAmount() {
        return balance;
    }

    public int getAccountType() {
        return accountType;
    }

    public boolean validateData() {
        if(accountType < 1 || accountType > 2) {
            return false;
        }
        return true;
    }

}
