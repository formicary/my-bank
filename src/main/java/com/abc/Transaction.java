package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {

    private final double amount;
    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
    }

    // Constructor to manually set transaction date, to aid in testing account interest calculations
    public Transaction(double amount, Date date) {
        this.amount = amount;
        this.transactionDate = date;
    }

    public double getAmount() {
        return this.amount;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

}
