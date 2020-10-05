package com.abc.entity.impl;

import com.abc.entity.Transaction;
import com.abc.exception.InputValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction class defines a single transaction made onto an account.
 * Uses immutable class design
 * @author aneesh
 */
public  class TransactionImpl implements Transaction {

    private final BigDecimal amount;
    private LocalDateTime transactionDate;

    public TransactionImpl(BigDecimal amount) {
        InputValidator.validateAmountNotNull(amount);
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "TransactionImpl{" +
                "amount=" + amount +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
