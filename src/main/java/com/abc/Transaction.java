package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = (new DateProvider()).now();
    }
    
    public Date getDate() {
    	return transactionDate;
    }
    
    public long getDays() {
    	 return TimeUnit.DAYS.convert(getDate().getTime(), TimeUnit.MILLISECONDS);
    }
    
    public long time_passed() {
        long ms = 0;
    	ms =  (new DateProvider()).now().getTime() - this.getDate().getTime(); 
    	return ms;
    }
    
    public long time_passed_in_days() {
    	long days_passed, ms = time_passed(); 
    	days_passed = TimeUnit.DAYS.convert(ms, TimeUnit.MILLISECONDS);
    	return days_passed;
    }

}
