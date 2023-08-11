package com.abc.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import com.abc.classes.Account.AccountType;

public class Transaction {
    private final double amount;

    private String transactionDate;

    public Transaction(double amount) {
        this.transactionDate = createTimeStamp();
        this.amount = amount;   
    }

    //Getters//
    public String getTransactionDate(){
        return transactionDate;
    }

    public double getTransactionAmount(){
        return amount;
    }

    public String createTimeStamp(){
        LocalDateTime localDateTime = LocalDateTime.now();

        //Format to be more readable
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(localDateTime);

        return formattedDateTime;
    }

    //Remove after testing
    public static void main(String[] args) {
    Transaction testTransaction = new Transaction(20);

    System.out.println("[" + testTransaction.transactionDate + "] " + testTransaction.amount);
}
}



