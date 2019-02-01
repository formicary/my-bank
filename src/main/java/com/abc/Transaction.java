package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;

    private final Date transactionDate;

    private final String transactionType;

    public Transaction(double amount, String transType) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
        transactionType = transType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
