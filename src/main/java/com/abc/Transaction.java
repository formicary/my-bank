package com.abc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Todo: revisit and also consider creating transactionType as enum
public class Transaction {
    private final BigDecimal amount;

    private LocalDateTime transactionDate;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}