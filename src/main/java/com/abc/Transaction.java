package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaction {
	
    private final double amount;
    private LocalDate transactionDate;
    
	public Transaction(LocalDate date, double amount) {
    	this.amount = amount;
        this.transactionDate = date;
        System.out.println("Transaction created for " + date);
    }
    
    public Transaction(double amount) {
        this(LocalDate.now(), amount);     
    }
    
    public LocalDate getTransactionDate() {
		return transactionDate;
	}
    
    public double getAmount() {
    	return this.amount;
    }
    
    /**
     * @return the time between this transaction and the start of the current day
     */
    public long timeSinceTransaction() {
     	return ChronoUnit.DAYS.between(LocalDate.now(), this.transactionDate);  	
    }
    
}
