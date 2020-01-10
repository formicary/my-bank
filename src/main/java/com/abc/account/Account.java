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
        StringBuilder statementStringBuilder = new StringBuilder();
        statementStringBuilder.append(this.getPrettyAccountType());
        statementStringBuilder.append("\n");
        double total = 0.0;
        for (Transaction t : this.transactions) {
            statementStringBuilder.append("  ");
            statementStringBuilder.append(t.amount < 0 ? "withdrawal " : "deposit ");
            statementStringBuilder.append(toDollars(t.amount));
            statementStringBuilder.append("\n");

            total += t.amount;
        }
        statementStringBuilder.append("Total ");
        statementStringBuilder.append(toDollars(total));
        return statementStringBuilder.toString();
    }

    public abstract double interestEarned();

    protected abstract String getPrettyAccountType();
}
