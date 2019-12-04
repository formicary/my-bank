package com.abc;

import java.util.Date;

public class Transaction {
    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER_IN,
        TRANSFER_OUT,
        INTEREST
    }

    private TransactionType type;

    private final double amount;

    private Date transactionDate;

    public Transaction(double amount, TransactionType type) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.type = type;
    }

    public Date getDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return type;
    }
}