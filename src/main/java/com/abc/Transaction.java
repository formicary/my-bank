package com.abc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author Pete
 */
public class Transaction {
    int account;
    double amount;
    String type;
    LocalDate date;
    LocalTime time;
    
    public Transaction(int account, double amount, String type){
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.date = java.time.LocalDate.now();
        this.time = java.time.LocalTime.now();
        
    }
    
    
}
