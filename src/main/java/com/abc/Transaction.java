package com.abc;

import java.time.LocalDateTime;

// Todo: revisit and also consider creating transactionType as enum
public class Transaction {
    private final double amount;

    private LocalDateTime transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}