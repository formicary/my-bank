package com.abc.bank;

import com.abc.utility.DateProvider;

import java.util.Date;

public class Transaction {
    private final double amount;

    protected Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

}
