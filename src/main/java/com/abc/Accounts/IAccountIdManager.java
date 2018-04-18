package com.abc.Accounts;

/**
 * Exposes methods to manage unique IDs for accounts in bank
 */
public interface IAccountIdManager {
    /**
     * Generates the account ID.
     *
     * @return The account ID.
     */
    int generateAccountId();
}
