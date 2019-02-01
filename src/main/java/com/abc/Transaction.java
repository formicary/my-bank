package com.abc;


import java.time.LocalDateTime;

// A transaction class, composed of a transaction date, type, and amount

public class Transaction {
    private final double amount;

    private final LocalDateTime transactionDate;

    private final String transactionType;

    public Transaction(double amount, String transType) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
        transactionType = transType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
