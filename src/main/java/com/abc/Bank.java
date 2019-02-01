package com.abc;

import java.util.ArrayList;
import java.util.List;
/**
 * Class which represents the bank, constructor and methods detailed below. 
 * 
 * @author Daniel Seymour
 * @version 1.0
 */
public class Bank {
    private List<Customer> customers;
    
    /**
     * Constructs an A List of Customer objects which is polymorphic as its dynamic type is
     * an ArrayList of Customer objects.
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Adds a customer to the List that calls this mutator.
     * 
     * @param customer The Customer object added to the list.
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Provides a summary of the names of the customers on the list and the number of accounts each owns.
     * 
     * @return A string representation of The customer names and their number of accounts.
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
     * Adds an "s" to a word if it describes a plural object.
     * 
     * @param number The number of items an object holds.
     * @param word The String representation of the word describing the object.
     * @return The word argument with or without an "s".
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Calculates the total interest paid by the bank to each customer on the list.
     * @return The sum of interest paid as a double.
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers) { // Scans the list for each member.
            total += c.totalInterestEarned(); // calls this method from the Customer class
        }
        return total;
    }

    /**
     * Accessor for the name of the first customer on the list.
     * @return String representation of the first customers name, or "Error" if the list is empty.
     */
    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
    
    /**
     * Prints the String given as the argument to the console.
     * @param str 
     */
    public void printString(String str) {
    	String output = str;
    	System.out.println(output);
    }
}
