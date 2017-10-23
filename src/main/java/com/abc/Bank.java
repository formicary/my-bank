package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Bank {
    private final List<Customer> CUSTOMERS;

    public Bank() {
        CUSTOMERS = Collections.synchronizedList(new ArrayList<Customer>());
    }

    public Bank addCustomer(Customer customer) {
        CUSTOMERS.add(customer);
        return this;
    }

    public String customerSummary() {
    	StringBuilder result = new StringBuilder();
    	result.append("Customer Summary");
        for (Customer c : CUSTOMERS) {
        	result.append("\n - ").append(c.getName()).append(" (");
        	result.append(pluralise(c.getNumberOfAccounts(), "account"));
        	result.append(')');
        }

        return result.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String pluralise(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public BigDecimal totalInterestPaid() {
        BigDecimal result = BigDecimal.ZERO;
        
        for(Customer c: CUSTOMERS) {
            result = result.add(c.totalInterestEarned());
        }
        
        return result;
    }

    public Customer getFirstCustomer() {
    	Customer result = null;
    	
    	if(CUSTOMERS.isEmpty() == false) {
    		result = CUSTOMERS.get(0);
    	}
    	
    	return result;
    }
    
    public void applyInterest() {
    	for(Customer c : CUSTOMERS) {
    		Iterator<Account> i = c.getAccountIterator();
    		Account a = null;
    		while(i.hasNext()) {
    			a = i.next();
    			a.deposit(a.interestEarned());
    		}
    	}
    }
    
    public void applyDailyInterest() {
    	for(Customer c : CUSTOMERS) {
    		Iterator<Account> i = c.getAccountIterator();
    		Account a = null;
    		while(i.hasNext()) {
    			a = i.next();
    			a.deposit(a.interestEarnedDaily());
    		}
    	}
    }
    
    public BigDecimal getTotalHoldings() {
    	BigDecimal result = BigDecimal.ZERO;
    	
    	for(Customer c : CUSTOMERS) {
    		result = result.add(c.totalAccountHoldings());
    	}
    	
    	return result;
    }
    
    @Override
    public String toString() {
    	return "Customers: " + CUSTOMERS.size() + "  Total Holdings: " + 
    			Transaction.toCurrecy(getTotalHoldings());
    }
}
