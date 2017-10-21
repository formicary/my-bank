package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double AMOUNT;

    private Date transactionDate;

    public Transaction(double amount) {
        this.AMOUNT = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
