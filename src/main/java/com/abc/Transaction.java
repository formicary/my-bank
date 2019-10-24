package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;
    private int type;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        if (amount >= 0) type = 0;
        else type = 1;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public String getType() {
        if (type == 0) return "Deposit";
        else return "Withdrawal";
    }
}
