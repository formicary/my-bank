package com.abc.MainClasses;
import java.util.Date;

import com.abc.Interfaces.TransactionInterface;

//Added get and set methods to improve code quality
public class Transaction implements TransactionInterface {
    private final double amount;
    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getAmount() {
        return amount;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}