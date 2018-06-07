package com.abc;

import java.time.LocalDateTime;

public class Transaction {
	
	//Define Instance Variables 
    private final double amount;
    private LocalDateTime transactionDate;

    //Transaction Constructor Method
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }
    
  //Transaction Constructor Method with Given Date
    public Transaction(double amount, LocalDateTime date) {
        this.amount = amount;
        this.transactionDate = date;
    }
    
    //Return Transaction Amount
    public double getAmount() {
    	return amount;
    }
    
    //Return Transaction Date and Time
    public LocalDateTime getDateTime() {
    	return transactionDate;
    }

}
