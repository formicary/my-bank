package com.abc.Transaction;

import com.abc.DateProvider;

import java.util.Calendar;
import java.util.Date;

public abstract class Transaction {
    final double amount;
    final Date transactionDate;

    Transaction(double amount){
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getAmount(){
        return amount;
    };

}
