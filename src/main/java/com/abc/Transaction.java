package com.abc;

import java.time.LocalDateTime;


public class Transaction {
	
    private final double amount;
    private final LocalDateTime transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.now();
    }
    
    public String getTransactionDate(){
        return DateProvider.formattedDate(transactionDate);
    }
    
    public double getAmount(){
    	return amount;
    }

}
