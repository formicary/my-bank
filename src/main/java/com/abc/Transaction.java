package com.abc;

import java.util.Date;


public class Transaction {

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL
    }
    private double amount;
    private Date transactionDate;
    private TransactionType transactionType;


    public Transaction(double amount,  Date transactionDate) {
        this.setAmount( amount, transactionDate);
    }

    public double getAmount() {
        return amount;
    }

    private void setAmount(double amount, Date transactionDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.setTransactionType();
    }


    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    private void setTransactionType() {
        this.transactionType = (this.amount > 0 ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL);
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

}

