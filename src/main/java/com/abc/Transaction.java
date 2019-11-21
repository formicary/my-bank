package com.abc;

import java.util.Date;

class Transaction {
    private final double amount;

    private Date transactionDate;

    Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Would not be necessary in production.
     * Sets the date the transaction occurred to that specified.
     * @param transactionDate a new transaction date.
     */
    void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
