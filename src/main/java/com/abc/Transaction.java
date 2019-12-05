package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        // Is there a better way to get date?
        this.transactionDate = DateProvider.getInstance().now();
    }

}
