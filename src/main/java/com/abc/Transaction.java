package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;    //should be a private variable and only accessibly via getter, to prevent changes

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public Date getDate(){  //getter
        return this.transactionDate;
    }
    
    public double getAmount(){  //getter
        return this.amount;
    }
}
