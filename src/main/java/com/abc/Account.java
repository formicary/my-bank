package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

abstract class Account {

    /**
     * Unique account number, which is given by the bank
     */
    protected long accountNumber;
    protected double balance = 0;
    List<Transaction> transactions = null;
    abstract double interestEarned();
    abstract String getType();

    public Account() {
         transactions = new ArrayList<Transaction>();
    }

    public void setAccountNumber(long accountNumber){
        this.accountNumber = accountNumber;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            updateTransactions(amount, null);
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }

        if(amount > balance){
            throw new IllegalArgumentException("amount must be less or equal to your balance");
        } else {
            updateTransactions(-amount, null);
        }
    }

    public void deposit(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            updateTransactions(amount, date);
        }
    }

    public void withdraw(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }

        if(amount > balance){
            throw new IllegalArgumentException("amount must be less or equal to your balance");
        } else {
            updateTransactions(-amount, date);
        }
    }

    private void updateTransactions(double amount, Date date) {
        if (date == null) {
            transactions.add(new Transaction(amount));
        } else {
            transactions.add(new Transaction(amount, date));
        }
        balance += amount;
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public double getBalance(){
        return balance;
    }

    public ArrayList<Transaction> getTransactionsBetween(Date from, Date to) {
        ArrayList<Transaction> selected = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            Date tempDate = t.getTransactionDate();
            if (tempDate.compareTo(from) >= 0 && tempDate.compareTo(to) <= 0) {
                selected.add(t);
            }
        }

        return selected;
    }
}
