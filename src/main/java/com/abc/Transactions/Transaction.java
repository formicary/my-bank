package com.abc.Transactions;

import java.util.Date;

/**
 * Represents a transaction.
 */
public class Transaction implements ITransaction {
    /**
     * The amount of money moved in this transaction.
     */
    private final double amount;

    /**
     * The date the transaction was carried out.
     */
    private Date transactionDate;

    /**
     * Initializes a new instance of the Transaction class.
     *
     * @param amount The amount of money moved in this transaction.
     * @param transactionDate The transaction date.
     */
    public Transaction(double amount, Date transactionDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    /**
     * Gets the amount of money moved in this transaction
     *
     * @return The amount of money moved in this transaction.
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * Gets the transaction date.
     *
     * @return The transaction date.
     */
    public Date getTransactionDate() {
        return this.transactionDate;
    }

    /**
     * Returns a string that represents this instance.
     *
     * @return The string that represents this instance.
     */
    @Override
    public String toString() {
        return String.format("[Transaction: amount=%s, transactionDate=%s]", this.amount, this.transactionDate);
    }
}
