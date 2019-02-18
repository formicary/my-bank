package com.abc;

import java.time.LocalDateTime;

public class Transaction {
    public final double amount;

    private LocalDateTime transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public LocalDateTime getTransactionDate() {
    	return transactionDate;
    }
}
