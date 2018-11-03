package com.abc;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transaction {
    private final double amount;
    private final LocalDateTime transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public double getAmount() {
    	return amount;
    }
    
    
    public LocalDateTime getDate() {
    	return transactionDate;
    }
    
    public String dateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return transactionDate.format(formatter);
    }
    
    

}
