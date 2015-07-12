package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Calendar transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance();
        this.transactionDate.setTime(Calendar.getInstance().getTime());
    }
    public Calendar getDate(){
        return this.transactionDate;
    }

    /*
    This is included for testing purposes
     */
    public void setDate(Date date){
        this.transactionDate.setTime(date);
    }

}
