package com.abc;

import java.util.Date;

/**
 * This class is an abstraction of a transaction (withdrawal, deposit, etc.)
 * @author Donald Campbell (campbell.donald@gmail.com)
 *
 */
public class Transaction {
    private final double amount;
    
    private Date transactionDate;
    
    /**
     * Standard constructor, assuming current system date as transaction date.
     * @param amount The amount of the transaction.
     */
    public Transaction(double amount) {
    	this(amount, DateProvider.getInstance().now());
    }
 
    /**
     * Constructor taking configurable transaction date.
     * @param amount Amount of the transaction.
     * @param date Transaction date.
     */
    public Transaction(double amount, Date date) {
    	this.amount = amount;
    	this.transactionDate = date;
    }
    
    /**
     * Getter method for the transaction date.
     * @return The transaction date.
     */
    public Date getTransactionDate() {
    	return transactionDate;
    }

    /** 
     * Getter method for the transaction amount.
     * @return The transaction amount.
     */
    public double getAmount() {
    	return amount;
    }
}
