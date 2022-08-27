package com.abc.account;

import com.abc.util.DateProvider;

import java.util.Date;

public class Transaction {
    private final double amount;
    private final Date transactionDate;
    private final TransactionType transactionType;

    public enum TransactionType {
        WITHDREW, DEPOSIT;
    }

    public Transaction(TransactionType transactionType, double amount) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getAmount() {
        if (transactionType == TransactionType.WITHDREW) {
            return -amount;
        }
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
