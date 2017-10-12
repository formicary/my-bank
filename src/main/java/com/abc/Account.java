package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {

    public enum Accounts{
        CHECKING, SAVINGS, MAXI_SAVINGS
    }

    protected List<Transaction> transactions;
    private final Accounts accountType;

    public Account(Accounts accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, DateProvider.getInstance().now()));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (amount > this.sumTransactions()) {
            throw new IllegalArgumentException("Insufficient funds for withdrawal");
        } else {
            transactions.add(new Transaction(-amount, DateProvider.getInstance().now()));
        }
    }

    public abstract double interestEarned();

    public Transaction lastWithdrawal() {
        //Transactions are ordered according to time
        for (int i = transactions.size() - 1; i >= 0 ; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() < 0) {
                return t;
            }
        }
        return null;
    }

    public static void transfer(Account from, Account to, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (from.sumTransactions() < amount) {
            throw new IllegalArgumentException("Insufficient funds for transfer");
        } else {
            Date time = DateProvider.getInstance().now();
            from.transactions.add(new Transaction(-amount, DateProvider.getInstance().now()));
            to.transactions.add(new Transaction(amount, DateProvider.getInstance().now()));
        }
    }

    public double sumTransactions() {
       double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    public Accounts getAccountType() {
        return accountType;
    }
}
