package com.abc;

import com.abc.util.DateProvider;

import java.util.Date;

public class Transaction {

    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
