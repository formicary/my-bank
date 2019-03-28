package com.abc;

import java.util.Date;

public class Transaction {

    public static final int DEPOSIT = 0;
    public static final int WITHDRAW = 1;
    public static final int TRANSFER = 2;     // TRANSFER should be considered only for WITHDRAW operations

    private final double amount;
    private final int transactionType;
    // Date field should also be final, but for the purpose of testing withdrawal time was changed for now
    private Date transactionDate;

    public Transaction(double amount, int transactionType) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getTransactionAmount() {
        return amount;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

}
