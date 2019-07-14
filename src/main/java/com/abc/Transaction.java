package com.abc;

import java.util.Date;

public class Transaction {

    enum TransactionType {
        MANUAL,
        INTEREST
    }

    private final double amount;
    private final Date transactionDate;
    private final TransactionType transactionType;

    public Transaction(double amount, TransactionType transactionType) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = Utils.nowDateAndTime();
    }

    public double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public String toString() {
        return "Transaction {" +
                "amount=" + amount + "," +
                "transactionType=" + transactionType.name() +
                '}';
    }
}
