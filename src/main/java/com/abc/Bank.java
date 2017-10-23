package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing a Bank which can have many Customers.
 * @author Christopher J. Smith
 */
public class Bank {
	
	//Object State variables.
    private final List<Customer> CUSTOMERS;

    /**
     * Constructor that initialises the Bank object for use.
     */
    public Bank() {
    	//Ensure that elements can be added concurrently.
        CUSTOMERS = Collections.synchronizedList(new ArrayList<Customer>());
    }

    /**
     * Add a customer to the bank.
     * @param customer is the customer you wish to add.
     * @return Returns a reference to the same Bank so you can chain commands.
     */
    public Bank addCustomer(Customer customer) {
    	//Ensure a valid customer was passed.
    	if(customer != null) {
    		CUSTOMERS.add(customer);
    	}
        return this;
    }

    /**
     * Generate a printable summary of the banks customers
     * @return Returns a printable summary of the banks customers.
     */
    public String getCutomersSummary() {
    	StringBuilder result = new StringBuilder();
    	result.append("Customer Summary");
        for (Customer c : CUSTOMERS) {
        	result.append("\n - ").append(c.getName()).append(" (");
        	result.append(pluralise(c.getNumberOfAccounts(), "account"));
        	result.append(')');
        }

        return result.toString();
    }

    /**
     * Internal method used to pluralise words if needed.
     * @param number Number of item the word is referring to
     * @param word The singular representation of the item.
     * @return Returns pluralised version of the word if Number is more than 1, else returns same word.
     */
    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String pluralise(int number, String word) {
    	if(word != null) {
    		return number + " " + (number == 1 ? word : word + "s");
    	} else {
    		return "";
    	}
    }

    /**
     * Used to get the sum of annual interest for all customers.
     * @return Returns the sum of annual interest.
     */
    public BigDecimal getTotalInterestPayableAnually() {
        BigDecimal result = BigDecimal.ZERO;
        
        for(Customer c: CUSTOMERS) {
            result = result.add(c.getTotalAnnualInterestPayable());
        }
        
        return result;
    }
    
    /**
     * Used to get the sum of daily interest for all customers.
     * @return Returns the sum of daily interest.
     */
    public BigDecimal getTotalInterestPayableDaily() {
        BigDecimal result = BigDecimal.ZERO;
        
        for(Customer c: CUSTOMERS) {
            result = result.add(c.getTotalDailyInterestPayable());
        }
        
        return result;
    }

    /**
     * Get the first customer added to the bank.
     * @return Returns the first customer added to the bank.
     */
    public Customer getFirstCustomer() {
    	Customer result = null;
    	
    	if(CUSTOMERS.isEmpty() == false) {
    		result = CUSTOMERS.get(0);
    	}
    	
    	return result;
    }
    
    /**
     * Apply the annual interest to all customer accounts.
     */
    public void applyInterest() {
    	//For each customer loop though all their accounts and deposit owed annual interest.
    	for(Customer c : CUSTOMERS) {
    		Iterator<Account> i = c.getAccountIterator();
    		Account a = null;
    		while(i.hasNext()) {
    			a = i.next();
    			a.deposit(a.getAnnualInterest());
    		}
    	}
    }
    
    /**
     * Apply the daily interest to all customer accounts.
     */
    public void applyDailyInterest() {
    	//For each customer loop though all their accounts and deposit owed daily interest.
    	for(Customer c : CUSTOMERS) {
    		Iterator<Account> i = c.getAccountIterator();
    		Account a = null;
    		while(i.hasNext()) {
    			a = i.next();
    			a.deposit(a.getDailyInterest());
    		}
    	}
    }
    
    /**
     * Get the total holdings of all customers in the bank.
     * @return Returns the total holdings of all customers in the bank.
     */
    public BigDecimal getTotalHoldings() {
    	BigDecimal result = BigDecimal.ZERO;
    	
    	for(Customer c : CUSTOMERS) {
    		result = result.add(c.getTotalAccountHoldings());
    	}
    	
    	return result;
    }
    
    @Override
    public String toString() {
    	return "Customers: " + CUSTOMERS.size() + "  Total Holdings: " + 
    			Transaction.toCurrecy(getTotalHoldings());
    }
}
