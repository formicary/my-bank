package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    /**
     * Create a new transaction. 
     * @param amount - create a transaction between accounts
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public Date getTransactionDate(){
        return transactionDate;
    }
    
     public void setTransactionDate(Date transactionDate){
        this.transactionDate = transactionDate;
    }
    
    
    
    
   

}
