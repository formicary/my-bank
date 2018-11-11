package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {

    private final double amount;
    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

//    For testing only
//    public void setTransactionDate(Date date) {
//        this.transactionDate = date;
//    }
}
