package com.abc.Accounts;

/**
 * Represents an interface for a factory for creating accounts.
 */
public interface IAccountFactory {
    /**
     * Creates a checking account.
     *
     * @param accountId The account ID.
     * @param customerId The customer ID.
     *
     * @return The checking account
     */
    CheckingAccount createCheckingAccount(int accountId, int customerId);

    /**
     * Creates a savings account.
     *
     * @param accountId The account ID.
     * @param customerId The customer ID.
     *
     * @return The savings account
     */
    SavingsAccount createSavingsAccount(int accountId, int customerId);

    /**
     * Creates a maxi-savings account.
     *
     * @param accountId The account ID.
     * @param customerId The customer ID.
     *
     * @return The maxi-savings account
     */
    MaxiSavingsAccount createMaxiSavingsAccount(int accountId, int customerId);
}
