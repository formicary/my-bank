package com.abc;

import java.time.LocalDateTime;


public class Transaction {

    private final double amount;
    private LocalDateTime transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}