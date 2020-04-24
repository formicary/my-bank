package com.abc;

import java.time.LocalDateTime;

public class Transaction {
    private final double amount;

    private LocalDateTime transactionDate;
    private String transactionType;
    private String accountType;

    public Transaction(double amount, String tranType, String accountType) {
        this.amount = amount;
        this.transactionType = tranType;
        this.accountType = accountType;

        this.transactionDate = LocalDateTime.now();
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getAccountType() {
        return accountType;
    }
}
