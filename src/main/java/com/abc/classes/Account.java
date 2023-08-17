package com.abc.classes;

import java.util.ArrayList;
import java.util.List;
import com.abc.helpers.AccountInterests;

public class Account {

    public enum AccountType {
        CHECKING, SAVINGS, MAXI_SAVINGS, MAXI_SAVINGS_PLUS
    }

    AccountType accountType;
    private List<Transaction> transactions;
    private double balance;
    private double accruedInterest;

    public Account(AccountType account) {
        this.accountType = account;
        this.balance = 0; // Assuming a new account opens with an empty balance
        this.accruedInterest = 0; // Assuming a new account opens with no acrrued interest
        this.transactions = new ArrayList<Transaction>();
    }

    // Getters//
    public Account getAccount() {
        return this;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Transaction getLatestTransaction() {
        Transaction latestTransaction = transactions.get(transactions.size() - 1);
        return latestTransaction;
    }

    public double getBalance() {
        return balance;
    }

    public double getAccruedInterest() {
        return accruedInterest;
    }

    // Transaction Functions//
    // Update balance, called everytime a deposit or withdrawal is made
    public void updateBalance(double amount) {
        balance += amount;
    }

    // Update the accrued interest, called everytime interest is added to the
    // account
    public void updateAccuredInterest(double amount) {
        accruedInterest += amount;
    }

    // Private deposit functions wrapped in public try catch functions
    private void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, "(IN)"));
            updateBalance(amount);
        }
    }

    private void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else if (amount > balance) {
            throw new IllegalArgumentException("Amount withdrawn must be less than your current balance");

        } else {
            transactions.add(new Transaction(-amount, "(OUT)"));
            updateBalance(-amount);
        }
    }

    public void tryDeposit(double amount) {
        try {
            // Attempt to make a deposit
            deposit(amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void tryWithdraw(double amount) {
        try {
            // Attempt to make a withdrawal
            withdraw(amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Interest functions//
    public void addInterest() {
        // Switch case based on what type of account it is, taken to
        // AccountInterests.java to handle the logic
        switch (accountType) {
            case CHECKING:
                AccountInterests.calculateInterestChecking(getAccount());
                break;
            case SAVINGS:
                AccountInterests.calculateInterestSavings(getAccount());
                break;
            case MAXI_SAVINGS:
                AccountInterests.calculateInterestMaxiSavings(getAccount());
                break;
            case MAXI_SAVINGS_PLUS:
                AccountInterests.calculateInterestMaxiSavingsPlus(getAccount());
                break;
            default:
                System.out.println("Could not find account with account type: " + accountType);
        }
    }
}
