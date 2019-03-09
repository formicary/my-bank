package com.abc;

import java.util.*;

public class Transaction implements ITransaction {
    private final double amount;
    private Calendar transactionDate;
    
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = new GregorianCalendar();
    }
    
    public Calendar getTransactionDate(){
        return transactionDate;
    }
    
    public void setTransactionDate(int year, int month, int day, int hour, int min, int sec){
    	this.transactionDate = new GregorianCalendar(year,month-1,day,hour,min,sec);
    }
    
    public double getTransactionAmount(){
    	return amount;
    }
    
    
}
