package com.abc.accounts;

import com.abc.Customer;
import com.abc.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {

    private List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            addTransaction(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            addTransaction(new Transaction(-amount));
        }
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public abstract double interestEarned();

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public String getAccountStatement() {
        String s = getPrettyAccountType();

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + Customer.Formatter.toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + Customer.Formatter.toDollars(total);
        return s;
    }

    protected List<Transaction> getTransactions() {
        return transactions;
    }

    protected abstract String getPrettyAccountType();

}
