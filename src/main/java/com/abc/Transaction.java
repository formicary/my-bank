package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a financial transaction with an amount and a date.
 */
public class Transaction {
    
    private final BigDecimal amount;
    private final LocalDate transactionDate;

    /**
     * Constructs a new Transaction with the given amount and the current date.
     *
     * @param amount The amount of the transaction.
     */
    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
    }

    /**
     * Gets the amount of the transaction.
     *
     * @return The transaction amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Gets the date of the transaction.
     *
     * @return The transaction date.
     */
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
}
