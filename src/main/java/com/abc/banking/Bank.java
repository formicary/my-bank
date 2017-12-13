package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

public final class Bank {
    private final Collection<Customer> customers = new LinkedHashSet<>();
    
    public Customer newCustomer(String customerName) {
    	if(customerName == null)
        	throw new IllegalArgumentException("customerName cannot be null");
    	
    	Customer newCustomer = new Customer(customerName);
    	customers.add(newCustomer);
    	return newCustomer;
    }
    
    public Collection<Customer> getCustomers() {
    	return Collections.unmodifiableSet(new LinkedHashSet<>(customers));
    }
    
    public BigDecimal totalInterestPaid() {
    	
    	return customers.stream()
    			.flatMap(customer -> customer.getAccounts().stream())
    			.flatMap(account -> account.getTransactions().stream())
    			.filter(transaction -> transaction.getTransactionDate().compareTo(LocalDate.now()) <= 0)
    			.filter(transaction -> transaction.isAccruedInterest())
    	    	.map(transaction -> transaction.getAmount())
    	    	.reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }
}
