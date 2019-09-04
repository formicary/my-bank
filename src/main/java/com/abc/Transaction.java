package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Class that contains the information of each action performed by an account
 * 
 * @author Accenture
 * @author Liam
 *
 */
public class Transaction {
    public final double amount; //amount involved with the transaction

    private Date transactionDate; // Date the tranaction occured

    /**
     * Constructor which takes in the amount involved and sets the amount and transactionDate attributes
     * @param amount Value added (or subtracted if negative) from the Accounts amount
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    /**
     * Getter for the transactionDate attribute
     * @return
     */
    public Date getTransactionDate() {
    	return transactionDate;
    }
    
    /**
     * Method that compares the transactionDate variable to the Date the method was called and returns
     * the difference in time in Days
     * @return The difference between the transactionDate and the method called Date, in days
     */
    public long tranactionAge() {
    	long currentTimeMillie = DateProvider.getInstance().now().getTime(); //get time at calling, in miliseconds
    	long transactionTimeMillie = transactionDate.getTime(); //convert the transactionDate to miliseconds
    	
    	long difference =  currentTimeMillie - transactionTimeMillie; 
    	long daysPassed = difference / (24*60*60*1000); //caluclate difference and divide by how many miliseconds are in a day
    	
    	return daysPassed;
    }
    

}
