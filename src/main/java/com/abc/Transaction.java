package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;
    public final int transactionType;

    private Date transactionDate;


    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();

        if(amount >= 0)
            //deposit
            transactionType = 1;
        else
            //withdraw
            transactionType = 0;
    }

    public Date getTransactionDate(){
        return transactionDate;
    }

    public int getTransactionType(){
        return transactionType;
    }

}
