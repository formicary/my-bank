package com.abc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Transaction {
    public final double amount;
    
    private DateTime transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now().toDateTime();
    }
    public Transaction(double amount, DateTime transDate) {
        this.amount = amount;
        this.transactionDate = transDate;
    }
    
    public String TransactionDate(){
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
        return dateFormat.print(this.transactionDate);
    }

}
