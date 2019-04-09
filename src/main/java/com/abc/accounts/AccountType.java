package com.abc.accounts;

import com.abc.domain.Transaction;

import java.util.List;

/**
 * An interface providing different strategies for different account types.
 */
public interface AccountType {

    /**
     * @return The name of this account type.
     */
    String getName();

    /**
     * Calculates the interest earned.
     *
     * @param balance      the actual balance of the account
     * @param transactions the list of transactions.
     * @return the amount of interest earned.
     */
    double interestEarned(double balance, List<Transaction> transactions);
}
