package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.abc.utilities.enums.TransactionType;

/**
 * Financial transaction that captures the transaction type, quantity and date
 */
public class Transaction {
    private final BigDecimal amount;
    private final TransactionType transactionType;
    private LocalDate transactionDate;

    /**
     * Initialises a new transaction instance object with a given amount, transaction type and date
     * @param amount quantity
     * @param transactionType deposit or withdrawal
     */
    public Transaction(BigDecimal amount, TransactionType transactionType) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = LocalDate.now();
    }

    /**
     * Gets the transaction amount
     * @return transaction amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Gets the type of transaction made
     * @return transaction type
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }
 
    /**
     * Gets the date and time the transaction took place
     * @return an immutable date time object
     */
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
}