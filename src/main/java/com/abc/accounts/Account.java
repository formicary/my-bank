package com.abc.accounts;

import com.abc.Transaction;
import java.util.ArrayList;
import java.util.List;

import static com.abc.util.CurrencyFormatter.toDollars;

public abstract class Account {

    private List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<>();
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

    //TODO Try implementing ToString here instead of subclasses

    public double sumTransactions() {
        return transactions.stream().mapToDouble(i -> i.amount).sum();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getStatement(){

        StringBuilder s = new StringBuilder(this.toString());
        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : transactions) {
            s.append("  ").append(t.amount < 0 ? "withdrawal " : "deposit ").append(toDollars(t.amount)).append("\n");
            total += t.amount;
        }
        s.append("Total ").append(toDollars(total));
        return s.toString();
    }
}
