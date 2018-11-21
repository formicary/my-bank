package com.abc;

import java.util.Date;

public class Transaction {
    // Changed to private for encapsulation.
    private final double amount;

    // Changed to final for security so it cannot be changed.
    private final Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getNow();
    }

    // Generated getter to be able to read the values so that they have a purpose.
    public double getAmount() {
        return amount;
    }

    // Generated getter to be able to read the values so that they have a purpose.
    public Date getTransactionDate() {
        return transactionDate;
    }

}
