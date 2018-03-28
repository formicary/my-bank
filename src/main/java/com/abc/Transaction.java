package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    //Constructor
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    //Get method for the date property
    public Date getDate(){
        return transactionDate;
    }
}
