package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
    
    // Constant variables 
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    // Variables
    private final int accountType;
    public List<Transaction> transactions;

    
    /**
     * Account class constructor. Takes an account type as a parameter and 
     * initialises the transactions array list
     * @param accountType CHECKING, SAVINGS or MAXI_SAVINGS
     */
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Adds a positive double into the array list of transactions in the form of
     * an account deposit. 
     * @param amount Double value to deposit
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Adds a negative double into the array list of transactions in the form of
     * an account withdrawal. 
     * @param amount Double value to withdraw
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    /**
     * Calculates the amount of interest earned based on which account is in use
     * and the total amount in the account through sumTransactions()
     * @return double Amount of interest earned
     */
    public double interestEarned() {
        double amount = sumTransactions();

        // Interest earned is 0 if amount is 0 or a negative number
        if (amount <= 0 ) {
            return 0;
        }

        switch (accountType) {
        case SAVINGS:
            if (amount <= 1000) {
                return amount * 0.001;
            } else {
                return 1 + (amount - 1000) * 0.002;
            }
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;

        case MAXI_SAVINGS:
            if (amount <= 1000) {
                return amount * 0.02;
            }
            if (amount <= 2000) {
                return 20 + (amount - 1000) * 0.05;
            }
            return 70 + (amount - 2000) * 0.1;

        default: // CHECKING
            if (amount > 1) {
                return amount * 0.001;
            } else {
                return 0;
            }
        }
    }

    /**
     * Function that checks if transactions exist in the array list and calculates
     * the total amount if true
     * @return double Total amount in account
     */
    public double sumTransactions() {
        if (transactionsExist()) {
            double amount = 0.0;
            for (Transaction t : transactions) {
                amount += t.amount;
            }
            return amount;
        } else {
            return 0;
        }
    }

    
    /**
     * Checks if the transactions array list is empty or not
     * @return Boolean True (not empty) or False (empty)
     */
    private Boolean transactionsExist() {
        return !transactions.isEmpty();
    }

    /**
     * Returns the account type in the form of an integer
     * @return int 0 (CHECKING), 1 (SAVINGS) or 2 (MAXI_SAVINGS)
     */
    public int getAccountType() {
        return accountType;
    }

}
