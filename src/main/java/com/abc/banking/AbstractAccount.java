package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

import com.abc.banking.exceptions.TransactionException;

public abstract class AbstractAccount implements Account {

    // transactions are unique
	private final Collection<Transaction> transactions = new TreeSet<>();
	
	private final long uniqueId = UniqueIdGenerator.getNext();
	
    public Transaction deposit(BigDecimal amount) {
    	
        if(amount == null)
        	throw new IllegalArgumentException("amount cannot be null");
    	
    	if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("amount must be greater than zero");

        Transaction transaction = new Transaction(amount);
        
        transactions.add(transaction);
        
        return transaction;
    }

	public Transaction withdraw(BigDecimal amount) throws TransactionException {
		
		if(amount == null)
        	throw new IllegalArgumentException("amount cannot be null");
		
		if (amount.compareTo(BigDecimal.ZERO) <= 0)
	        throw new IllegalArgumentException("amount must be greater than zero");
	    
	    if(amount.compareTo(getBalance()) > 0)
	    	throw new TransactionException("amount cannot be greater that the account balance");

	    Transaction transaction = new Transaction(amount.negate());
	    
	    transactions.add(transaction);
	    
	    return transaction;
	}
	
	public void rollBackTransactionIfExists(Transaction transaction) {
		if(transaction == null)
			return;
		
		if(transactions.contains(transaction))
			transactions.remove(transaction);
	}
	
	public Collection<Transaction> getTransactions() {
		return Collections.unmodifiableSet(new TreeSet<>(transactions));
	}
	
    public BigDecimal getBalance() {
    	
        return getBalanceAt(LocalDate.now());
    }
    
    public BigDecimal getBalanceAt(LocalDate date) {
    	
    	return transactions.stream()
    	.filter(transaction -> transaction.getTransactionDate().compareTo(date) <= 0)
    	.map(transaction -> transaction.getAmount())
    	.reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }
    
    private LocalDate getLastDateInterestAccrued() {
    	
    	return transactions.stream()
					.filter(transaction -> transaction.isAccruedInterest())
					.map(transaction -> transaction.getTransactionDate())
					.max(LocalDate::compareTo) //
					.orElse(LocalDate.MIN);
    }
    
    private LocalDate getMinDayOfTransaction() {
    	return transactions.stream()
				.map(transaction -> transaction.getTransactionDate())
				.min((a, b) -> a.compareTo(b))
				.orElse(LocalDate.now());
    }
    
    public void ensureInterestAccrued() {
		
		if(transactions.isEmpty())
			return;
		
		LocalDate lastDateInterestAccrued = getLastDateInterestAccrued();
		LocalDate startDate = null;
		
		if(lastDateInterestAccrued == LocalDate.MIN) 
			startDate = getMinDayOfTransaction();
		else
			startDate = lastDateInterestAccrued.plusDays(1);
		
		for(LocalDate day = startDate; day.compareTo(LocalDate.now()) < 0; day = day.plusDays(1))
			transactions.add(new Transaction(getDailyInterest(day), day, true));
	}
    
    @Override
    public int hashCode() {
    	return Long.hashCode(uniqueId);
    }
    
    @Override
    public boolean equals(Object obj) {
    	return this == obj;
    }
}
