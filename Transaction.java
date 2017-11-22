package com.abc;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;

    private LocalDate transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
