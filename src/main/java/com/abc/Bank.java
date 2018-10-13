package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 */
public interface Bank {
	
	/**
	 * 
	 * @return the name of the branch 
	 */
	public String branchName();

	/**
	 * Add new customer to the bank 
	 * @param customer the customer to be added
	 */
	public void addCustomer(Customer customer);

	/**
	 * Get summary of the customers
	 * @return the summary text
	 */
    public String customerSummary();
    
    /**
     * Get the total interest paid by the bank 
     * @return the amount of interest 
     */
    public double totalInterestPaid();
      
    /**
     * 
     * @return The first customer of the bank
     */
    public String getFirstCustomer();
         
    
}
