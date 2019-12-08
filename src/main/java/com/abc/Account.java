package com.abc;

import com.abc.types.AccountType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that handles the customer's account
 */
public class Account {

    /**
     * Type of account
     */
    private final AccountType accountType;

    /**
     * List containing all the transactions
     */
    private List<Transaction> transactions;

    /**
     * Constructor of the Account class
     * @param accountType the type of the account
     */
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Function to deposit money into the account
     * @param amount amount to deposit into the account
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        transactions.add(new Transaction(amount));
    }

    /**
     * Function to withdraw money from the account
     * @param amount amount to withdraw from the account
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        transactions.add(new Transaction(-amount));
    }

    /**
     * Function to get the interest earned from the account
     * @return the interest earned from the account
     */
    public double interestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                }
                return 1 + (amount - 1000) * 0.002;
            case MAXI_SAVINGS:
                if (hasWithdrawalInPastDays(10)) {
                    return amount * 0.001;
                }
                return amount * 0.05;
            default:
                return amount * 0.001;
        }
    }

    /**
     * Function to provide the sum of transactions on the account
     * @return the balance of the account
     */
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.getAmount();
        }
        return amount;
    }

    /**
     * Function to transfer money between the accounts
     * @param account account that will receive the money
     * @param amount amount that will be transferred
     */
    public void transferTo(Account account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        if (amount > sumTransactions()) {
            throw new IllegalArgumentException("amount must be smaller than balance");
        }
        transactions.add(new Transaction(-amount));
        account.transactions.add(new Transaction(amount));
    }

    /**
     * Function to check if the account has a withdrawal in the past ten days
     * @return boolean that checks if the account has a withdrawal in the past 10 days
     */
    private boolean hasWithdrawalInPastDays(int days) {
        Date tenDaysAgo = new DateProvider().daysAgo(10);
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            if (t.getTransactionDate().after(tenDaysAgo) && t.getAmount() < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for the account type
     * @return account type
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * Getter for the transactions
     * @return list of transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

}
