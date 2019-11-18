package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * Class correlating to bank accounts.
 */
class Account {
    private final AccountType accountType;

    private double balance = 0;
    private List<Transaction> transactions;

    Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Deposits funds into the corresponding account.
     * @param amount the amount of money to deposit into the account
     */
    void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("The deposit amount must exceed zero.");
        else {
            transactions.add(new Transaction(amount));
            balance += amount;
        }
    }

    /**
     * Withdraws funds from the corresponding account.
     * @param amount the amount of money to withdraw from the account
     */
    void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("The withdrawal amount must exceed zero.");
        else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
        }
    }

    /**
     * Determines the interest earned by the bank account.
     * @return the amount made via interest for the corresponding account
     */
    double interestEarned() {
        switch (accountType) {
            case SAVINGS:
                return (balance <= 1000) ? balance * 0.001 : 1 + (balance - 1000) * 0.002;
            case MAXI_SAVINGS:
                if (balance <= 1000) return balance * 0.02;
                else if (balance <= 2000) return 20 + (balance - 1000) * 0.05;
                else return  70 + (balance - 2000) * 0.1;
            default:
                return balance * 0.001;
        }
    }

    double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Checks for the existence of transactions within the corresponding account.
     * @return whether the account has made any transactions
     */
    boolean hasTransactions() {
        return !transactions.isEmpty();
    }

    AccountType getAccountType() {
        return accountType;
    }

    String getAccountTypeToString() {
        switch (accountType) {
            case SAVINGS:
                return "Savings Account\n";
            case MAXI_SAVINGS:
                return "Maxi Savings Account\n";
            default:
                return "Checking Account\n";
        }
    }
}
