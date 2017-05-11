package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

//    private final int transactionType;

    public Transaction(double amount) {
        this.amount = amount;
//        this.transactionType = transactionType;
        this.transactionDate = DateProvider.getInstance().now();
    }

//    public int getTransactionType(){
//        return transactionType;
//    }

    public Date getTransactionDate(){
        return transactionDate;
    }

}
