package com.abc;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = new Date();//same as DateProvider.getInstance().now();
    }
    
    //@param date to compare transaction date with
    // used as the current time throughout program
    public boolean transactionIsOverTenDaysOld(Date currentDate){
    	long diffInMilliSeconds=Math.abs(currentDate.getTime()-transactionDate.getTime());
    	long diffInDays=TimeUnit.DAYS.convert(diffInMilliSeconds, TimeUnit.MILLISECONDS);
    	return (diffInDays<10 ? false : true);
    }
    
    public double getAmount() {
    	return amount;
    }

}
