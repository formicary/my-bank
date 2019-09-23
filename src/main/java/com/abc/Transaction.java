package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {

    private double amount;
    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
    }

    /**
    * Gets the amount that has been transacted.
    *
    * @return The amount as a double.
    */
    public double getAmount(){
    	return amount;
    }

    /**
    * Gets the exact time of the transaction.
    *
    * @return The transaction time, as type Date.
    */
    public Date getDate(){
    	return transactionDate;
    }

}
