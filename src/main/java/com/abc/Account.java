package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    public List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void deposit(double amount) {
        this.deposit(amount, LocalDate.now());
    }

    public void withdraw(double amount, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public void withdraw(double amount) {
        this.withdraw(amount, LocalDate.now());
    }

    public double getBalance() {
        double balance = 0.0;
        for (Transaction tx: transactions) {
            balance += tx.amount;
        }
        return balance;
    }

    public String getStatement() {
        String statement = this.accountType() + "\n";

        for (Transaction tx : this.transactions) {
            statement += "  " + tx + "\n";
        }

        statement += "Total " + Transaction.toDollars(this.getBalance()) + "\n";
        return statement;
    }

    public abstract double interestEarned();

    public abstract String accountType();

}
