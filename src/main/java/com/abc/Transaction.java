package com.abc;

import java.util.Date;

public class Transaction {

    private final double amount;
    private Date transactionDate;
    private boolean isWithdrawable;

    public Transaction(double amount, double v, boolean isWithdrawable) {

        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.isWithdrawable = isWithdrawable;

    }


    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getTransactionAmount() {
        return amount;
    }

}
