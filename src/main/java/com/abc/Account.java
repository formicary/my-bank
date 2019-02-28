package com.abc;

import java.lang.Math;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public abstract class Account {

    public List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, date));
        }
    }

    public void deposit(double amount) {
        this.deposit(amount, LocalDate.now());
    }

    public void withdraw(double amount, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount, date));
        }
    }

    public void withdraw(double amount) {
        this.withdraw(amount, LocalDate.now());
    }

    public double sumTransactions() {
        double balance = 0.0;
        for (Transaction tx: transactions) {
            balance += tx.amount;
        }
        return balance;
    }

    public double currentBalance() {
        if (this.transactions.size() == 0) return 0.0;

        Iterator<Transaction> iter = this.transactions.iterator();
        Transaction tx = iter.next();

        double balance = 0.0;
        for (LocalDate date = tx.date; !date.isAfter(LocalDate.now()); date = date.plusDays(1)) {
            balance += dailyInterest(balance);

            if (date.isEqual(tx.date)) {
                balance += tx.amount;
                if (iter.hasNext()) tx = iter.next();
            }
        }

        return balance;
    }

    public double interestEarned() {
        return this.currentBalance() - this.sumTransactions();
    }

    public String getStatement() {
        String statement = this.accountType() + "\n";

        for (Transaction tx : this.transactions) {
            statement += "  " + tx + "\n";
        }

        statement += "Total " + Transaction.toDollars(this.currentBalance()) + "\n";
        return statement;
    }

    public abstract String accountType();

    public abstract double dailyInterest(double balance);

}
