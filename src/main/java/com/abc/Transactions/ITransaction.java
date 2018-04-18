package com.abc.Transactions;

import java.util.Date;

/**
 * Represents an interface transaction.
 */
public interface ITransaction {
    /**
     * Gets the amount of money moved in this transaction
     *
     * @return The amount of money moved in this transaction.
     */
    double getAmount();

    /**
     * Gets the transaction date.
     *
     * @return The transaction date.
     */
    Date getTransactionDate();
}
