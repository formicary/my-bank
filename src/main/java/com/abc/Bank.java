package com.abc;

/**
 * This is a naive implementation of a banking system of ABC bank.
 * The application is capable of creating different types of accounts for
 * users. The user can deposit, withdraw, and transfer money between own accounts. 
 * Additionally, every type of account has its own interest rate accumulation system.
 * The application is not meant for concurrent (multithreaded) access and is not therefore
 * thread safe.
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.abc.exceptions.InvalidCustomerName;
import com.abc.executors.AbstractInterestCalculator;
import com.abc.executors.DailyInterestRateExecutor;
import com.abc.executors.InterestRateExecutor;


final public class Bank {
    
	/**
     * Current list of all customers in the bank.
     */
	final private List<Customer> customers;
	/**
	 * Executor service responsible of interest rate computation on all existing accounts. 
	 */	
	private InterestRateExecutor executor;
  
	/**
	 * Constructor that creates an instance of the bank with automatic interest rate recalculation 
	 * facility that is run once a day.
	 */
    public Bank() {
        customers = new ArrayList<Customer>();
        AbstractInterestCalculator task = new AbstractInterestCalculator() {
        		@Override
        		public void execute() {
        			for (Customer c: customers) {
        				c.updateInterestEarned();
        			}
        		}
        };
        setInterestRateExecutor(new DailyInterestRateExecutor(task)); 
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
            	if (executor!=null) executor.shutdown();
            }
        }));
    } 

     // This method does not check for the duplicates.
     // It should be used very thoroughly. 
    private void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    /**
     * This method creates a new customer for ABC bank.
     * This is the only proper way to create a customer.
     * Note, that customers with similar names are treated as different customers.
     * Name should contain only letters starting with a capital letter several names followed by space are allowed.
     * @param name unique name of the new costumer.
     * @return customer new costumer of this bank.
     * @throws InvalidCustomerName if customer name coul not be validated.
     */
    public Customer createCustomer (final String name) throws InvalidCustomerName {
    	Customer customer = new Customer(name);
    	addCustomer(customer);
    	return customer;
    }
    
    /**
     * @return brief summary of the customer including name and number of the accounts.
     */
    
    public String customersSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Customer Summary");
        for (Customer c : customers)
            summary.append(System.lineSeparator())
            		.append(" - ")
            		.append(c.getName())
            		.append(" (")
            		.append(format(c.getNumberOfAccounts(), "account"))
            		.append(")");
        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in ends with 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number % 10 == 1 ? word : word + "s");
    }
    
    /**
     * Method to set different interest rate executor service.
     * @param executor The executor needs to implement InterestRateExecutor interface.
     */
    public void setInterestRateExecutor(InterestRateExecutor executor) {
    	if (executor == null) throw new NullPointerException();
    	//stop previous executor if exists
    	if (this.executor!=null) executor.shutdown();
    	this.executor = executor;
    	executor.start();    		
    }
 

    /**
     * @return total interest paid to all the costumers registered with this bank.
     */
    public BigDecimal totalInterestPaid() {
        BigDecimal total = BigDecimal.ZERO;
        for(Customer c: customers) {
            total = total.add(c.totalInterestEarned().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
   
}
