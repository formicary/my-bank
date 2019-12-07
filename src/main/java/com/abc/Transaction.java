package com.abc;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Transaction {
    public final double amount;
    private final String label;
    private final DateTime transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.label = "UNSPECIFIED";
        this.transactionDate = LocalDateTime.now().toDateTime();
    }
    public Transaction(double amount, String label) {
        this.amount = amount;
        this.label = label;
        this.transactionDate = LocalDateTime.now().toDateTime();
    }
    public Transaction(double amount, String label, DateTime transDate ) {
        this.amount = amount;
        this.label = label;
        this.transactionDate = transDate;
    }
    
    public String getLabel(){
        return this.label;
    }
    public String TransactionDate(){
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
        return dateFormat.print(this.transactionDate);
    }

}
