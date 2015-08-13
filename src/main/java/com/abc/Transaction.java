package com.abc;

import java.util.Date;

public class Transaction {

    //TODO change amount type to BigDecimal
    public final double amount;
    private final Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
