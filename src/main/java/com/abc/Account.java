package com.abc;

import java.util.ArrayList;
import java.util.Date;
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
     * @throws IllegalArgumentException If the amount parameter is less than 1
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
     * @throws IllegalArgumentException If the amount parameter is less than 1
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }
    
    /**
     * Adds a transaction into the array list of transactions. This method allows
     * a date to be input as a parameter, to add transactions in the past
     * @param amount Double value to withdraw (negative val) or deposit (positive val)
     * @param date Date that the transaction occurred
     */
    public void addTransaction(double amount, Date date) {
        transactions.add(new Transaction(amount, date));
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
        case MAXI_SAVINGS:
            DateProvider dp = new DateProvider();
            Transaction lastTrans = getLastWithdrawal();
            
            if (lastTrans != null) {
                if (DateProvider.getDateDiff(lastTrans.getTransactionDate(),
                                             dp.now()) > 10) {
                    return amount * 0.05;
                } else {
                    return amount * 0.001;
                }
            } else {
                return amount * 0.05;
            }
        default: // CHECKING
            return amount * 0.001;
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
     * Returns the most recent withdrawal transaction made in this account
     * @return Transaction or Null depending on if a withdrawal exists
     */
    private Transaction getLastWithdrawal() {
        for (int i = transactions.size() - 1; i >= 0; i--) {
            if (transactions.get(i).amount < 0) {
                return transactions.get(i);
            }
        }
        return null;
    }

    /**
     * Returns the account type in the form of an integer
     * @return int 0 (CHECKING), 1 (SAVINGS) or 2 (MAXI_SAVINGS)
     */
    public int getAccountType() {
        return accountType;
    }

}
