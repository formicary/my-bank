package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;
    private final String statement;
    private Date transactionDate;

    public Transaction(double amount, String statement) {
        this.amount = amount;
        this.statement = statement;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getAmount() {
        return amount;
    }

    public String getStatement() {
        return statement;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
