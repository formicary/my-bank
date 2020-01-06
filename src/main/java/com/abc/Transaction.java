package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    protected Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
