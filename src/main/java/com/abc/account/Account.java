package com.abc.account;

import com.abc.Transaction;

import java.util.ArrayList;
import java.util.List;

import static com.abc.util.StringFormatter.toDollars;

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

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.amount;
        return amount;
    }


    public String generateStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getPrettyAccountType());
        sb.append("\n");
        double total = 0.0;
        for (Transaction t : this.transactions) {
            sb.append("  ");
            sb.append(t.amount < 0 ? "withdrawal " : "deposit ");
            sb.append(toDollars(t.amount));
            sb.append("\n");

            total += t.amount;
        }
        sb.append("Total ");
        sb.append(toDollars(total));
        return sb.toString();
    }

    public abstract double interestEarned();

    public abstract String getPrettyAccountType();

}
