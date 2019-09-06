package com.abc;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Transaction {
    public final double amount;

    public final Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public int daysToDate(Date date) {
        long diff = this.transactionDate.getTime() - date.getTime();
        int days = (int) TimeUnit.MILLISECONDS.toDays(diff);

        return days;
    }

}
