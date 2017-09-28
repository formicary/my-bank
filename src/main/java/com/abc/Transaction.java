package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;
    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    /**
     * 
     * @return transaction date
     */
    public Date getTransDate(){
        return this.transactionDate;
    }
    
    /**
     * 
     * @return transaction amount
     */
    public double getAmount(){
        return this.amount;
    }
}
