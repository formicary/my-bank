package com.abc;

import java.util.Calendar;

public class Transaction {
    public final double amount;

    private Calendar transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance();
    }

    public Calendar getTransactionDate() {
        return transactionDate;
    }
}
