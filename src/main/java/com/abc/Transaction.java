package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public Date timestamp(){
        return this.transactionDate;
    }

    //FOR TESTING PURPOSES ONLY
    //PLEASE DON'T USE THIS
    public void setTime(Date time){ this.transactionDate = time; }

}
