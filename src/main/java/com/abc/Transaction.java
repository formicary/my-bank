package com.abc;

import java.time.Instant;

import static java.lang.Math.abs;

public class Transaction {

    private final double amount;
    private Instant transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = Instant.now();
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public static String toDollars(double amount) {
        return String.format("$%,.2f", abs(amount));
    }

    public String getTransactionDetails() {
        String transactionType = amount < 0 ? "withdrawal" : "deposit";
        return transactionType + " " + toDollars(amount);
    }
}
