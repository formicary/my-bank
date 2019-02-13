package com.abc;

import java.util.Date;

public class Transaction {
    private final double amount;
    private Date transactionDate;

    Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = new Date(System.currentTimeMillis());
    }

    //Constructor to allow checking for a transaction made at specific time
    Transaction(double amount, Date transactionDate){
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public double getAmount(){
        return amount;
    }

    public Date getTransDate(){
        return transactionDate;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Transaction)) return false;
        Transaction b = (Transaction) o;
        long dateDifference = transactionDate.getTime() - b.getTransDate().getTime();

        //Check amount and date are the same allowing for a small time difference to account for using local clock speed
        return ((amount == b.getAmount()) && (dateDifference < 2));
    }
}
