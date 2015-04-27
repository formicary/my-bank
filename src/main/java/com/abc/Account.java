package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

public class Account {

    public enum AccountType {
        CHECKING,
        SAVINGS,
        MAXI_SAVINGS
    }

    private AccountType accountType;
    public List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions) {
            amount += t.amount;
        }
        return amount;
    }

    // Get the date of the latest transaction
    public Date getLatestTransaction() {
        Date latest = null;
        for (Transaction t: transactions) {
            latest = t.getTransactionDate();
        }
        return latest;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    // Added one more condition: balance of the account >= amount
    public void withdraw(double amount) {
        double balance = sumTransactions();
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (balance < amount) {
            throw new IllegalArgumentException("amount must be less than the current balance");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    // Checking Account Interest
    public double checkingInterest(double amount, long days) {
        double earned = amount * 0.001;
        earned = (earned / 365) * (double)days;
        return earned;
    }

    // Savings Account Interest
    public double savingsInterest(double amount, long days) {
        double earned = 0.0;
        if(amount <= 1000) {
            earned = amount * 0.001;
        } else {
            earned = 1 + (amount-1000) * 0.002;
        }
        earned = (earned / 365) * (double)days;
        return earned;
    }

    // Modified Maxi_Savings Account Interest
    public double maxisavingsInterest(double amount, long days) {
        double earned = 0.0;

        //days stores number of days from the latest transaction to the current date.
        // If it is greater than 10, it means there are no transactions for past 10 days.
        // If it is equal to 10, it means it's been exactly 10 days since the latest transaction
        // If it is less than 10, it means there is at least one transaction within past 10 days.
        if(days > 10) {
            earned = amount * 0.05;
        } else {
            earned = amount * 0.001;
        }

        earned = (earned / 365) * (double)days;
        return earned;
    }

    public double interestEarned() {
        double amount = sumTransactions();
        Date latestTransaction = getLatestTransaction();
        Date current = DateProvider.getInstance().now();
        long days = DateProvider.getInstance().daysBetween(latestTransaction, current);
        double earned = 0.0;
        switch(accountType){
            case SAVINGS:
                earned = savingsInterest(amount, days);
                break;
            case MAXI_SAVINGS:
                earned = maxisavingsInterest(amount, days);
                break;
            default:
                earned = checkingInterest(amount, days);
        }
        return earned;
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
