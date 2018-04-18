package com.abc.Accounts;

import com.abc.Transactions.TransactionException;

/**
 * Represents an interface for an account.
 */
public interface IAccount {
    /**
     * Gets the customer ID.
     *
     * @return The customer ID.
     */
    int getCustomerId();

    /**
     * Gets the account ID.
     *
     * @return The account ID.
     */
    int getAccountId();

    /**
     * Deposits the given amount into this bank account.
     *
     * @param amount The amount to be deposited.
     *
     * @throws TransactionException Thrown when the transaction failed.
     */
    void deposit(double amount) throws TransactionException;

    /**
     * Withdraws the given amount from this bank account.
     *
     * @param amount The amount to be withdrawn.
     *
     * @throws TransactionException Thrown when the transaction failed.
     */
    void withdraw(double amount) throws TransactionException;

    /**
     * Calculates the interest earned so far.
     *
     * @return The amount of interest earned.
     */
    double calculateInterestEarned();

    /**
     * Returns the total amount of money moved in all transactions.
     *
     * @return The total amount of money moved in all transactions.
     */
    double sumTransactions();

    /**
     * Gets a statement for this account.
     *
     * @return The statement.
     */
    String getAccountStatement();
}
