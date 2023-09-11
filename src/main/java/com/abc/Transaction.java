package com.abc;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private LocalDate transactionDate;

    private TransactionType transactionType;

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
