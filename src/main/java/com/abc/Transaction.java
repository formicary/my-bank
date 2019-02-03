package com.abc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class Transaction {
	public enum TRANSACTION_TYPE {
        DEPOSIT, WITHDRAWAL;
		
    	public String toString() {
    		return name().toLowerCase();
    	}
    }
	
    private final BigDecimal amount;
    private LocalDateTime transactionDate;
    private final TRANSACTION_TYPE transactionType;

    public Transaction(BigDecimal amount, TRANSACTION_TYPE transactionType) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
        this.transactionType = transactionType;
    }

	public BigDecimal getAmount() {
		return amount;
	}
	
	public Boolean withinLast10Days() {
		LocalDateTime now = LocalDateTime.now();
		
		long daysBetween = ChronoUnit.DAYS.between(transactionDate, now);
		
		return daysBetween <= 10;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public TRANSACTION_TYPE getTransactionType() {
		return transactionType;
	}
    
}
