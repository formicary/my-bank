package com.abc;

import java.util.Date;

public class Transaction {

    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    /**
     * another constructor with date make easier to test
     * @param amount
     * @param transactionDate
     */
    public Transaction(double amount, Date transactionDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
