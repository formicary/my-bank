package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 */
public class BankBranch implements Bank{
    private List<Customer> customers;
    private String branchName;

    public BankBranch(String branchName) {
        customers = new ArrayList<Customer>();
        this.branchName = branchName;
    }
    
    /**
	 * 
	 * @return the name of the branch 
	 */
	public String branchName() {
		return branchName;
	}
	
	/**
	 * Add new customer to the bank 
	 * @param customer the customer to be added
	 */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
	 * Get summary of the customers
	 * @return the summary text
	 */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Get the total interest paid by the bank 
     * @return the amount of interest 
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * 
     * @return The first customer of the bank
     */
    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
