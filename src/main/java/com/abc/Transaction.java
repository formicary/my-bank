package com.abc;

import java.util.Calendar;


public class Transaction {
    public final double amount;
    public final Calendar transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
}


