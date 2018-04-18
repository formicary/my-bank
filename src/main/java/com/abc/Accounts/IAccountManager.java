package com.abc.Accounts;

import java.util.List;

/**
 * Represents an interface for an account manager.
 */
public interface IAccountManager {
    /**
     * Returns the number of accounts opened by the customer.
     *
     * @return The number of accounts opened by the customer.
     */
    int getNumberOfAccounts();

    /**
     * Gets the accounts that correspond to the given customer ID.
     *
     * @param customerId The customer ID.
     *
     * @return The accounts
     */
    List<IAccount> getAccounts(int customerId);

    /**
     * Opens an account in the name of the given customer ID.
     *
     * @param customerId The customer ID.
     * @param accountType The account type.
     *
     * @returns The ID of the newly opened account.
     */
    int openAccount(int customerId, AccountType accountType);

    /**
     * Withdraws the given amount from a customer's account.
     *
     * @param customerId The customer ID.
     * @param accountId The account ID.
     * @param amount The amount of money to be withdrawn.
     */
    void withdraw(int customerId, int accountId, double amount);

    /**
     * Deposits the given amount into a customer's account.
     *
     * @param customerId The customer ID.
     * @param accountId The account ID.
     * @param amount The amount of money to be deposited.
     */
    void deposit(int customerId, int accountId, double amount);

    /**
     * Transfers the given amount from the source account ID to the destination account ID.
     *
     * @param customerId The customer ID.
     * @param sourceAccountId The ID of the source account.
     * @param destinationAccountId The ID of the destination account.
     * @param amount The amount of money to transfer.
     */
    void transfer(int customerId, int sourceAccountId, int destinationAccountId, double amount);

    /**
     * Calculates the total interest earned across all accounts for a given customer ID.
     *
     * @return The total interest.
     */
    double calculateTotalInterestEarned(int customerId);
}
