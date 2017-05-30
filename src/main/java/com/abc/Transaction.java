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


    public boolean withinTenDays() {
        Date now = DateProvider.getInstance().now();
        if(now.getTime() - transactionDate.getTime() < 1000 * 60 * 60 * 24 * 10) return true;
        return false;
    }

}
