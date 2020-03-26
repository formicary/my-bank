package com.accenture.mybank;

import java.util.Date;

import com.accenture.mybank.utils.DateProvider;
public class Transaction {
    public final double amount;
    private Date transactionDate;
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Transaction(double amount, Date date) {
        this.amount = amount;
        this.transactionDate = date;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}