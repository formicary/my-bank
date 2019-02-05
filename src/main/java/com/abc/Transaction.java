package com.abc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author t-pizzle
 */
public class Transaction {

 
    private final double amount;
    private LocalDateTime transactionDate;
    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now().minusDays(new java.util.Random().nextInt(20)+1);
        //this.transactionDate = DateProvider.getInstance().now();
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
