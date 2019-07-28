package com.abc;

import java.util.Calendar;
import java.util.Date;


public class Transaction {

    private final String title;
    private final double amount;
    private final Date transactionDate;

    /**
     * A transaction consists of a title, amount, and a record of the date and time it was created
     */
    public Transaction(String title, double amount) {
        this.title = title;
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
    }

    public String getTitle() {
        return title;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }
}
