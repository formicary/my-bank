package com.abc.Accounts;

import com.abc.Utils.IDateProvider;

import java.util.Date;

/**
 * Represents a dummy implementation of an account
 */
public class DummyAccount extends AbstractAccount{
    /**
     * Initializes a new instance of the DummyAccount class.
     *
     * @param dateProvider The date provider.
     * @param accountId The account ID.
     * @param customerId The customer ID.
     */
    public DummyAccount(IDateProvider dateProvider, int accountId, int customerId) {
        super(dateProvider, accountId, customerId);
    }

    /**
     * Returns a string representing the account type.
     *
     * @return The string representing the account type.
     */
    protected String getAccountType() {
        return "Dummy";
    }

    /**
     * Calculates the new balance with the accrued interest added between dates of the two given transactions.
     *
     * @param initialBalance The initial balance.
     * @param _1 The date of the first transaction.
     * @param _2 The date of the second transaction
     *
     * @return The balance with interest added.
     */
    protected double accrueInterest(double initialBalance, Date _1, Date _2) {
        return initialBalance;
    }

    /**
     * Returns a string that represents this instance.
     *
     * @return The string that represents this instance.
     */
    @Override
    public String toString() {
        return String.format(
                "[DummyAccount: accountId=%s, customerId=%s, transactions=%s]", this.accountId, this.customerId, this.transactions);
    }
}
