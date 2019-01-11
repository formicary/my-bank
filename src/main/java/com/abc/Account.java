package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public abstract class Account {
    public List<Transaction> transactions;

    public Account() {
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
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public abstract double interestEarned();

    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.amount;

        System.out.println("After deposit: " + amount);
        return amount;
    }

    public abstract String statementForAccount();

    public String totalTransactions(){
        double total = 0.0;
        String s = "";

        for (Transaction t : transactions){
            s += "  " + t.getTransactionDetails() + "\n";
            total += t.amount;
        }

        s += "Total " + Transaction.toDollars(total);
        return s;
    }

}
