package com.abc;

import java.util.Date;
import java.util.Calendar;
/*-----------------------------------------------------------------------------
                            Transaction Class
-----------------------------------------------------------------------------*/
public class Transaction{
    private final double amount;
    private long id;
    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
        this.id= (long) (Math.random() * 1000000000);
    }

    public long getTransactionID(){
        return this.id;
    }

    public double getTransactionAmount(){
        return this.amount;
    }

    public Date getTransactionDate(){
        return this.transactionDate;
    }

}
