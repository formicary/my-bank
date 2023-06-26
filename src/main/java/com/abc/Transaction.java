package com.abc;

import com.abc.ENUMS.TransactionType;

import java.time.LocalDate;


public class Transaction {
    private final double amount;

    public double getAmount() {
        return amount;
    }

    private TransactionType transactionType;

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    private LocalDate transactionDate;



    public Transaction(double amount,TransactionType transactionType) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType=transactionType;
    }

}
