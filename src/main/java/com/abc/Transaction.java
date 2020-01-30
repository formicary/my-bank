package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Transaction {
	
    private final double amount;
    private Date transactionDate;
    
    //A transaction is created with an amount and the current time.
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    //Access the amount of transaction.
    public double getAmount() {
    	return amount;
    }
    
    //Access the time when the transaction happened.
    public Date getTime() {
    	return transactionDate;
    }
    
    /*This method was created and is only used for testing: maxiSavingsAccount3() in BankTest.java (test has been @Ignored).
     * This method sets the time for a transaction to 1 January 2020. 
     */
	public void setTime() { 
		 transactionDate = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(); 
	}

}
