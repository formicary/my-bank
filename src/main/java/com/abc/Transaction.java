package com.abc;

import java.util.Date;

import com.abc.DateUtils.DateProvider;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getTransactionAmount() {
        return amount;
    }

    public boolean isAfter(Date date) {

        return transactionDate.after(date);
    }

}
