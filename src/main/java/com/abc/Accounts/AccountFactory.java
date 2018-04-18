package com.abc.Accounts;

import com.abc.Utils.IDateProvider;

/**
 * Represents a factory for creating accounts.
 */
public class AccountFactory implements IAccountFactory {
    /**
     * The date provider.
     */
    private IDateProvider dateProvider;

    /**
     * Initializes a new instance of the AccountFactory class.
     *
     * @param dateProvider The date provider.
     */
    public AccountFactory(IDateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    /**
     * Creates a checking account.
     *
     * @param accountId The account ID.
     * @param customerId The customer ID.
     *
     * @return The checking account
     */
    public CheckingAccount createCheckingAccount(int accountId, int customerId) {
        return new CheckingAccount(this.dateProvider, accountId, customerId);
    }

    /**
     * Creates a savings account.
     *
     * @param accountId The account ID.
     * @param customerId The customer ID.
     *
     * @return The savings account
     */
    public SavingsAccount createSavingsAccount(int accountId, int customerId) {
        return new SavingsAccount(this.dateProvider, accountId, customerId);
    }

    /**
     * Creates a maxi-savings account.
     *
     * @param accountId The account ID.
     * @param customerId The customer ID.
     *
     * @return The maxi-savings account
     */
    public MaxiSavingsAccount createMaxiSavingsAccount(int accountId, int customerId) {
        return new MaxiSavingsAccount(this.dateProvider, accountId, customerId);
    }
}
