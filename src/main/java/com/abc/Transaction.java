package com.abc;

import java.util.Date;

public class Transaction {

    double amount;
    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setAmount(double amo) {
        if (amount == 0) {
            this.amount = amo;
        } else {
            this.amount += amo;
        }
    }
}
