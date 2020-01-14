package com.abc;

import com.abc.util.IDateProvider;

import java.util.Date;

public class Transaction {
    private final double getAmount;
    private final Date transactionDate;

    public Transaction(double amount, IDateProvider dateProvider) {
        this.getAmount = amount;
        this.transactionDate = dateProvider.getCurrentDate();
    }

    public double getAmount() {
        return getAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
