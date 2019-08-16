package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private List<Transaction> transactions = new ArrayList<Transaction>();
    protected double accountValue = 0.0;

    private final int accountType;

    public Account(int accountType) {
        this.accountType = accountType;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            accountValue += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            accountValue -= amount;
        }
    }

    public abstract double interestEarned();
//    public double interestEarned() {
//        double amount = sumTransactions();
//
//        switch(accountType){
//            case SAVINGS:
//                if (amount <= 1000)
//                    return amount * 0.001;
//                else
//                    return 1 + (amount-1000) * 0.002;
//            case MAXI_SAVINGS:
//                if (amount <= 1000) return amount * 0.02;
//                if (amount <= 2000) return 20 + (amount-1000) * 0.05;
//                return 70 + (amount-2000) * 0.1;
//            default:
//                return amount * 0.001;
//        }
//    }

    public double getAccountValue() {
        return accountValue;
    }

    // Check if something is of a certain class.
    public int getAccountType() {
        return accountType;
    }

}
