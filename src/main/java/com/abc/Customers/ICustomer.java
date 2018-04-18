package com.abc.Customers;

import com.abc.Accounts.AccountType;

/**
 * Represents an interface for a customer.
 */
public interface ICustomer {
    /**
     * Gets the name of the customer.
     *
     * @return The name of the customer.
     */
    String getName();

    /**
     * Gets the ID of the customer.
     *
     * @return The ID of the customer.
     */
    int getCustomerId();

    /**
     * Opens an account in the name of the given customer ID.
     *
     * @param accountType The account type.
     *
     * @returns The ID of the newly opened account.
     */
    int openAccount(AccountType accountType);

    /**
     * Gets the number of accounts opened by the customer.
     *
     * @return The number of accounts opened by the customer.
     */
    int getNumberOfAccounts();

    /**
     * Calculates the total interest earned across all accounts.
     *
     * @return The total interest earned across all accounts.
     */
    double calculateTotalInterestEarned();

    /**
     * Gets an overall statement for the costumer's accounts.
     *
     * @return The statement.
     */
    String getStatement();
}
