package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    private String type;
    private Date transactionDate;
    public Transaction(double amount,String type) {
        this.type = type;
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public String getType() {
        return type;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

}