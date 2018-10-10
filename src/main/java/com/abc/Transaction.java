package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    private final Date transactionDate;
//constructor method constisting of amount and date.
    public Transaction(double amount, Date transactionDate) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
    }
//method to get transactions amount
    public double getAmount(){
        return this.amount;
    }
    public Date getDate(){
        return this.transactionDate;
    }
}