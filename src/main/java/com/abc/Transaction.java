package com.abc;

import java.util.Date;

import static com.abc.TransactionType.WITHDRAW;

public class Transaction {
    private final double amount;
    private final TransactionType transactionType;
    private Date transactionDate;

    public Transaction(double amount, TransactionType transactionType) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        if (transactionType == WITHDRAW) {
            return -amount;
        } else {
            return amount;
        }
    }

    public double getAbsAmount() {
        return Math.abs(amount);
    }
}
