package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public long getTransactionAge() {
        long diffInMillies = DateProvider.getInstance().now().getTime() - transactionDate.getTime();
        long diffInDays = (diffInMillies / (60*60*24*1000));
        return diffInDays;
    }
}
