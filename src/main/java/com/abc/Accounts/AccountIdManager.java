package com.abc.Accounts;

/**
 * Exposes methods to manage unique IDs for accounts in a bank.
 */
public class AccountIdManager implements IAccountIdManager {
    /**
     * The account ID manager instance.
     */
    private static final AccountIdManager instance = new AccountIdManager();

    /**
     * The latest account ID.
     */
    private int latestAccountId;

    /**
     * Initializes a new instance of the AccountIdManager class.
     */
    private AccountIdManager() {
        this.latestAccountId = 0;
    }

    /**
     * Returns the single instance of this AccountIdManager class.
     *
     * @return The single instance of this AccountIdManager class.
     */
    public static AccountIdManager getInstance() {
        return instance;
    }

    /**
     * Generates the account ID.
     *
     * @return The account ID.
     */
    public int generateAccountId() {
        this.latestAccountId++;

        return this.latestAccountId;
    }

    /**
     * Returns a string that represents this instance.
     *
     * @return The string that represents this instance.
     */
    @Override
    public String toString() {
        return String.format(
                "[AccountIdManager: accountId=%s]", this.latestAccountId);
    }
}
