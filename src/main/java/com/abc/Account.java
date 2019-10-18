package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

public abstract class Account {
    private ArrayList<Transaction> transactions;
    private String name;
    private double balance;
    private Date lastWithdrawal;

    public Account(String name) {
        this.name = name;
        this.balance = 0;
        this.transactions = new ArrayList<Transaction>();
    }

    protected void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            balance += amount;
            String statement = "deposit " + toDollars(amount);
            transactions.add(new Transaction(amount,statement));
        }
    }

    protected void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            balance -= amount;
            String statement = "withdrawal " + toDollars(amount);
            transactions.add(new Transaction(-amount,statement));
            lastWithdrawal = DateProvider.getInstance().now();
        }
    }

    public void transfer(Account account,double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            balance -= amount;
            String statement = "transfered " + toDollars(amount) + " to " + account.getName();
            transactions.add(new Transaction(-amount,statement));
            String statement2 = "recieved " + toDollars(amount) + " by " + getName();
            account.getTransactions().add(new Transaction(amount,statement));
        }
    }

    public abstract double interestEarned();

    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getName() {
        return name;
    }

    public String getBalanceinDollars() {
        return toDollars(balance);
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public Date getLastWithdrawal() {
        return lastWithdrawal;
    }

    public void setLastWithdrawal(Date lastWithdrawal) {
        this.lastWithdrawal = lastWithdrawal;
    }
}
