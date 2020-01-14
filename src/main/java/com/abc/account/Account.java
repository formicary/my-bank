package com.abc.account;

import com.abc.Transaction;
import com.abc.util.DateProvider;

import java.util.ArrayList;
import java.util.Date;
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
            transactions.add(new Transaction(amount, DateProvider.getInstance()));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount, DateProvider.getInstance()));
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction transaction : transactions)
            amount += transaction.getAmount();
        return amount;
    }


    public String generateStatement() {
        StringBuilder statementStringBuilder = new StringBuilder();
        statementStringBuilder.append(this.getPrettyAccountType());
        statementStringBuilder.append("\n");
        double total = 0.0;
        for (Transaction transaction : this.transactions) {
            statementStringBuilder.append("  ");
            statementStringBuilder.append(transaction.getAmount() < 0 ? "withdrawal " : "deposit ");
            statementStringBuilder.append(toDollars(transaction.getAmount()));
            statementStringBuilder.append("\n");

            total += transaction.getAmount();
        }
        statementStringBuilder.append("Total ");
        statementStringBuilder.append(toDollars(total));
        return statementStringBuilder.toString();
    }

    public abstract double interestEarned();

    protected abstract String getPrettyAccountType();
}
