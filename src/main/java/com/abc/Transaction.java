package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        transactionDate = DateProvider.getInstance().now();
    }

    public int transactionAge(){
        long milliseconds = DateProvider.getInstance().now().getTime() - transactionDate.getTime();
        return (int) (milliseconds / (1000*60*60*24));
    }
}
