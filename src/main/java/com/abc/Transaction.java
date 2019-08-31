package com.abc;

import java.util.Date;

class Transaction {
    private final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getAmount() {
        return amount;
    }
    public Date getDate(){
        return transactionDate;
    }
}
