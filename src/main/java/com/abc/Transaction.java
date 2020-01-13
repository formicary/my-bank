package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Date getTransactionDate(){
        return new Date(transactionDate.getTime());
    }

    //for testing
    public void setTransactionDate(Date newDate){
        transactionDate = new Date(newDate.getTime());
    }

}
