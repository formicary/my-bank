package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    protected final double amount;
    private Date transactionDate;

    protected Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
}
