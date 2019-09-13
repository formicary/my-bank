package com.abc;

import com.abc.DateProvider;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;
    private final Date transactionDate;

    public Transaction(double amount){
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getAmount(){
        return amount;
    };

}
