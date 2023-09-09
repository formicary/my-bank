package com.abc;

import java.util.Date;

/**
 * Represents a financial transaction with an amount and a date.
 */
public class Transaction {
    
    private final double amount;
    private final Date transactionDate;

    /**
     * Constructs a new Transaction with the given amount and the current date.
     *
     * @param amount The amount of the transaction.
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    /**
     * Gets the amount of the transaction.
     *
     * @return The transaction amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the date of the transaction.
     *
     * @return The transaction date.
     */
    public Date getTransactionDate() {
        return transactionDate;
    }
}
