package com.mybank;

import com.mybank.Utlities.DateProvider;
import com.mybank.Utlities.TransactionType;

import java.time.LocalDate;

public class Transaction {
    public double amount;
    private final LocalDate transactionDate;
    private final TransactionType transactionType;

    public Transaction(double amount, TransactionType transactionType ) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
