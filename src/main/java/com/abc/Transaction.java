package com.abc;

import java.time.LocalDateTime;

public class Transaction {
    private final double amount;

    private TransactionType transactionType;

    private LocalDateTime transactionDate;

    public Transaction(double amount, TransactionType transactionType, LocalDateTime transactionDate) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @SuppressWarnings("unused")
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
