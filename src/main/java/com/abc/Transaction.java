package com.abc;

import java.util.Date;

public class Transaction {

    private final double amount;
    private final Date transactionDate;

    public Transaction(double amount) {
        if (amount == 0) {
            throw new IllegalArgumentException("Amount must not be zero.");
        }

        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public String getType() {
        return (amount < 0) ? "withdrawal" : "deposit";
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
