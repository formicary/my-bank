package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public static final int Deposit = 0;
    public static final int Withdrawel = 1;
    public static final int Interest = 2;


    public final double amount;
    private Date transactionDate;
    private int transsactionType;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Transaction(double amount, Date tDate, int type) {
        this.amount = amount;
        this.transactionDate = tDate;
        this.transsactionType = type;
    }

    // Return true if the transactions is a withdrawel(amount is negative), else return false
    public int getTransactionType(){
        return transsactionType;
    }
}
