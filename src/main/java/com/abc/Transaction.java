package com.abc;

import java.time.LocalDate;

public class Transaction {
    public final double amount;

    public LocalDate transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
