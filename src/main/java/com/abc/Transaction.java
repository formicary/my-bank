package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    private final Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.INSTANCE.now();
    }

    public Date getTransactionDate() {
        // Return a defensive copy to avoid mutation of date
        return new Date(transactionDate.getTime());
    }

}
