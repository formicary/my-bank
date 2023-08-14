package com.abc.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final double amount;

    private String transactionDate;
    private String transactionType;

    public Transaction(double amount, String transactionType) {
        this.transactionDate = createTimeStamp();
        this.amount = amount; 
        this.transactionType = transactionType;  
    }

    //Getters//
    public String getTransactionDate(){
        return transactionDate;
    }

    public double getTransactionAmount(){
        return amount;
    }

    public String getTransactionType(){
        return transactionType;
    }

    //Timestamp Function//
    public String createTimeStamp(){
        LocalDateTime localDateTime = LocalDateTime.now();

        //Format to be more readable
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(localDateTime);

        return formattedDateTime;
    }
}



