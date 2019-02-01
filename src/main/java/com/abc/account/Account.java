package com.abc.account;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public abstract class Account {

    public enum AccountType {CHECKING, SAVINGS, MAXI_SAVINGS};

    protected List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount deposited must be greater than zero.");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount withdrawn must be greater than zero.");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double balance() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public abstract String accountStatement();

    public double interestEarned() {
        double amount = balance();
        if (amount == 0.0) {
            return 0.0;
        }
        return ( Math.round(interestEarnedAfter(amount, 365, getRate(amount)) * 100.0) / 100.0 ) - amount;
    }

    protected double interestEarnedAfter(double amount, int days, double rate) {
      if (days < 1) {
        return amount;
      }
      double newAmount = addInterest(amount, rate);
      return interestEarnedAfter(newAmount, days-1, getRate(newAmount));
    }

    protected static double addInterest(double amount, double rate) {
        return amount + (amount * rate/365.0);
    }

    protected abstract double getRate(double balance);

    public abstract Account.AccountType getAccountType();

}
