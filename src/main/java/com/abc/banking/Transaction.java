package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.abc.banking.config.ApplicationConfig;

public final class Transaction implements Comparable<Transaction> {
    private final BigDecimal amount;
    private final LocalDate transactionDate;
    private final boolean accruedInterest;
    private final long uniqueId;

    protected Transaction(BigDecimal amount, LocalDate transactionDate, boolean accruedInterest) {
        
    	if(amount == null)
    		throw new IllegalArgumentException("amount cannot be null");
    	
    	if(transactionDate == null)
    		throw new IllegalArgumentException("transactionDate cannot be null");
    	
    	this.amount = amount.setScale(ApplicationConfig.MONETARY_DECIMAL_PLACES_ALLOWED, ApplicationConfig.MONETARY_ROUNDING_MODE);
        this.transactionDate = transactionDate;
        this.accruedInterest = accruedInterest;
        this.uniqueId = UniqueIdGenerator.getNext();
    }
    
	public Transaction(BigDecimal amount) {
    	this(amount, LocalDate.now(), false);
    }
	
	public boolean isAccruedInterest() {
		return accruedInterest;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	
	@Override
	public int compareTo(Transaction o) {
		
		if(o == null)
			throw new IllegalArgumentException("Transaction to be compared cannot be null.");
		
		if(this.equals(o))
			return 0;
		
		int comparison = this.transactionDate.compareTo(o.transactionDate);
		
		if(comparison != 0)
			return comparison;
		
		if(this.accruedInterest != o.accruedInterest) {
			
			if(this.accruedInterest)
				return 1;
			else
				return -1;
		}

		comparison = Long.compare(this.uniqueId, o.uniqueId);
		
		return comparison;
	}
	
	@Override
	public boolean equals(Object obj) {
		return 
				obj != null &&
				obj instanceof Transaction &&
				this.amount.equals(((Transaction)obj).amount) &&
				this.transactionDate.equals(((Transaction)obj).transactionDate) &&
				this.accruedInterest == ((Transaction)obj).accruedInterest &&
				this.uniqueId == ((Transaction)obj).uniqueId;
	}
	
    @Override
    public int hashCode() {
    	return Long.hashCode(uniqueId);
    }
}