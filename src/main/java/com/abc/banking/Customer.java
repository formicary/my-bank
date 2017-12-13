package com.abc.banking;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.abc.banking.exceptions.AccountOpeningException;
import com.abc.banking.exceptions.TransactionException;

public final class Customer {
    private String name;
    private final Collection<Account> accounts = new LinkedHashSet<>(); // one customer can have the same account only once
    private final long uniqueId = UniqueIdGenerator.getNext();
    
    protected Customer(String name) {
        if(name == null)
        	throw new IllegalArgumentException("Name cannot be null");
    	
    	this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	if(name == null)
        	throw new IllegalArgumentException("Name cannot be null");
    	
    	this.name = name;
    }

    
    public <T extends Account> T openAccount(Class<T> accountClass) throws AccountOpeningException {
    	if(accountClass == null)
        	throw new IllegalArgumentException("accountClass cannot be null");
    	
    	T newAccount;
    	
    	try {
	    	newAccount = accountClass.newInstance();
	    	
    	} catch (ReflectiveOperationException ex) {
    		throw new AccountOpeningException("Cannot instantiate " + accountClass.getName(), ex);
    		
		} 
    	
    	accounts.add(newAccount);
    	
    	return newAccount;
    }

    public Pair<Transaction, Transaction> transferBetweenAccounts(Account source, Account destination, BigDecimal amount) throws TransactionException {
    	
    	if(source == null)
        	throw new IllegalArgumentException("source cannot be null");

    	if(destination == null)
        	throw new IllegalArgumentException("destination cannot be null");

    	if(amount == null)
        	throw new IllegalArgumentException("amount cannot be null");

    	if(!accounts.contains(source))
    		throw new TransactionException("The source account does not belong to specified customer.");
    	
    	if(!accounts.contains(destination))
    		throw new TransactionException("The destination account does not belong to specified customer.");
    	
    	Transaction withdrawalTransaction = null;
    	Transaction depositTransaction = null;
    	
    	try {
	    	withdrawalTransaction = source.withdraw(amount);

    	} catch(TransactionException ex) {
    		source.rollBackTransactionIfExists(withdrawalTransaction);
    		
    		throw new TransactionException("Transfer between accounts cannot debit source account.", ex);
    	
    	} finally {
    		depositTransaction = destination.deposit(amount);
    	}

    	return new ImmutablePair<>(withdrawalTransaction, depositTransaction);
    }
    
    public Collection<Account> getAccounts() {
    	return Collections.unmodifiableCollection(new LinkedHashSet<>(accounts));
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    @Override
    public boolean equals(Object o) {
    	return this == o;
    }
    
    @Override
    public int hashCode() {
    	return Long.hashCode(uniqueId);
    }

}