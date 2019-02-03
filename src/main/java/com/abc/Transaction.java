package com.abc;

import java.time.LocalDate; // changed all methods to implement new up to data Java Date class

public class Transaction {
    public final double amount;

    private LocalDate transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    // getter for transaction date
    public LocalDate getTransactionDate() {
        return transactionDate;
    }

}
