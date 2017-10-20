package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    public final Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Transaction(double amount, Date transactionDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

}
