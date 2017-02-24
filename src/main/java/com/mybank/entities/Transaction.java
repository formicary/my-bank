package com.mybank.entities;

import java.time.temporal.ChronoUnit;
import java.util.GregorianCalendar;

public class Transaction {
	
	private final double amount;
	private GregorianCalendar transactionDateTime;

	public Transaction(double amount) {
		this.amount = amount;
		transactionDateTime = new GregorianCalendar();
	}
	
	public GregorianCalendar getTransactionDateTime(){
		return transactionDateTime;
	}
	
	public double getTransactionAmount(){
		return amount;
	}
	public long getDaysToNextTransaction(GregorianCalendar nextTransactionDateTime){
	   
		return Math.abs(ChronoUnit.DAYS.between(transactionDateTime.toInstant(),nextTransactionDateTime.toInstant()));
		
	}

	public boolean isMadeToday() {
		
		long daysSinceTransactionCreated = getDaysToNextTransaction(new GregorianCalendar());
		if(daysSinceTransactionCreated < 1) return true;
		else return false;
	}
}
