package com.abc;

import java.util.ArrayList;
import java.util.List;

/*A class to contain Bank : name and accounts


@author Hans-Wai Chan

*/

public class Bank {
    private List<Customer> customers;

    /**
     *  Constructor which initialises with an empty list of Customers
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Method to add a new Customer for Bank.
     * 
     * @param customer
     * 			(Object) Customer: The new customer to be added
     * 
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    /**
     * Method to return the customer summary for this bank.
     * 
     * @return summary
     * 			String: contains information on name and number of accounts. 
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }
    
    /**
     * Method to format string for number and pluralisation 
     * 
     * Make sure correct plural of word is created based on the number passed in:
     * If number passed in is 1 just return the word otherwise add an 's' at the end
     * 
     * @param number
     * 		int: used to determine singular or plural
     * @param word
     * 		String: base word (does not include unique singular terms
     * @return
     * 		String: Number with corresponding word.
     */

    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Method to calculate the total interest paid to ALL accounts of ALL customers.
     * @return 
     * 		double: total interest paid to ALL accounts of ALL customers
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * Method to give the name of the first customer of the bank.
     * 
     * @return name 
     * 		String: name of the first customer.
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
