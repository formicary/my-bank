package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        transactions.add(new Transaction(0.00));
    }

    public int getAccountType() {
        return accountType;
    }

    public void deposit(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("amount must be greater than zero");
        else
            transactions.add(new Transaction(amount));
    }

    public void withdraw(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("amount must be greater than zero");
        else
            transactions.add(new Transaction(-amount));
    }

    public double totalAccount() {
        double transactions = sumTransactions();
        double interest = interestEarned();

        return transactions + interest;
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.amount;

        return amount;
    }

    public abstract double interestEarned();

}
