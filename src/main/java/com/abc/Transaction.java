package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    private Date transactionDate;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Transaction(double amount, Date tDate) {
        this.amount = amount;
        this.transactionDate = tDate;
    }

    // Return true if the transactions is a withdrawel(amount is negative), else return false
    public boolean transactionType(){
        return Double.doubleToLongBits(amount) < 0;
    }
}
