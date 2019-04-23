package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fearg
 *
 */
public class Bank {

	private List<Customer> customers;

	/**
	 * constructor that creates an empty arraylist of customers
	 */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * adds a new customer to the bank's list
     * @param customer
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * 
     * @return
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        
        for (Customer c : customers) {
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        }
        
        return summary;
    }

    /**
     * if customer has multiple accounts then the word accounts is returned, 
     * otherwise the word account is returned
     * @param number
     * @param word
     * @return
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * adds all the interest earned by all the bank's customers
     * @return
     */
    public double totalInterestPaid() {
        double total = 0;
        
        for(Customer c: customers) {
            total += c.totalInterestEarned();
        }
        return total;
    }

    /**
     * if there are customers then return the name of the first customer
     * else catch exception
     * @return
     * @throws Exception 
     */
    public String getFirstCustomer() throws Exception {
        if(!customers.isEmpty()) {
            return customers.get(0).getName();
        } 
        else{
            throw new Exception("No customers");
        }
    }
}
