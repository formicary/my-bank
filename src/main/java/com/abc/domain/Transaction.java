package com.abc.domain;

import java.time.LocalDate;

/**
 * Represents a transaction.
 * A transaction has an amount, and a date.
 */
public class Transaction {

    private final double amount;

    private LocalDate transactionDate;

    /**
     * Creates a new transaction.
     *
     * @param amount the amount in Dollar. Positive: deposit, negative: withdraw.
     * @param date   the date of the transaction.
     */
    public Transaction(final double amount, final LocalDate date) {
        this.amount = amount;
        this.transactionDate = date;
    }

    /**
     * @return the amount in Dollar. Positive: deposit, negative: withdraw.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return the date of the transaction.
     */
    public LocalDate getDate() {
        return transactionDate;
    }

}
