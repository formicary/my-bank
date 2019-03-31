package com.abc;

import java.util.Date;

public class Transaction {
    public static final int DEPOSIT = 0;
    public static final int WITHDRAWAL = 1;
    public static final int INTEREST = 2;


    public final double amount;
    private Date transactionDate;
    private int transactionType;

    public Transaction(double amount, Date tDate, int type) {
        this.amount = amount;
        this.transactionDate = tDate;
        this.transactionType = type;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Return true if the transactions is a withdrawal(amount is negative), else return false
     */
    public int getTransactionType() {
        return transactionType;
    }
}
