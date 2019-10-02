package com.abc.accounts;

import com.abc.Transaction;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

//    public static final int CHECKING = 0;
//    public static final int SAVINGS = 1;
//    public static final int MAXI_SAVINGS = 2;
//
//    private final int accountType;
    protected List<Transaction> transactions;
    protected double balance;
    protected double intRate;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0.0;
        this.intRate = 0.0;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getBalance() {
        return balance;
    }

    public double getIntRate() {
        return intRate;
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
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

//    public double interestEarned() {
//        double amount = sumTransactions();
//        switch(accountType){
//            case SAVINGS:
//                if (amount <= 1000)
//                    return amount * 0.001;
//                else
//                    return 1 + (amount-1000) * 0.002;
////            case SUPER_SAVINGS:
////                if (amount <= 4000)
////                    return 20;
//            case MAXI_SAVINGS:
//                if (amount <= 1000)
//                    return amount * 0.02;
//                if (amount <= 2000)
//                    return 20 + (amount-1000) * 0.05;
//                return 70 + (amount-2000) * 0.1;
//            default:
//                return amount * 0.001;
//        }
//    }

}