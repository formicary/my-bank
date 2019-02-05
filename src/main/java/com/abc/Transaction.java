package com.abc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

 
    private final double amount;
    private LocalDateTime transactionDate;
    // Could be an enum that has different date formats depending on Locale.
    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        
        //Line below was used for testing
        //this.transactionDate = DateProvider.getInstance().now().minusDays(new java.util.Random().nextInt(20)+1);
    }
    public LocalDateTime getTransactionDate(){
        return transactionDate;
    }
    
    public double getAmount(){
        return amount;
    }

    @Override
    public String toString() {
        return "Amount = " + amount + ", - Date = " + transactionDate.format(DATE_FORMAT);
    }
    

}
