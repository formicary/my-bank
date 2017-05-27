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
        	transactionType = 1; //deposit
        else
        	transactionType = 0;  //withdraw
    }
    
    public Date getTransactionDate(){
    	return transactionDate;
    }
    
    public int getTransactionType(){
    	return transactionType;
    }

}
