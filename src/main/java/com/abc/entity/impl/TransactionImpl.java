package com.abc.entity.impl;

import com.abc.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction class defines a single transaction made onto an account.
 * Uses immutable class design
 * @author aneesh
 */
public  class TransactionImpl implements Transaction {
    /**
     * amount of funds in transaction
     */
    private final BigDecimal amount;

    /**
     * date and time of transaction
     */
    private LocalDateTime transactionDate;

    public TransactionImpl(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
