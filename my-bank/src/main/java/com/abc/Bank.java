package com.abc;

import java.util.ArrayList;
import java.util.List;


/**
* This class is a representation of a bank.
*
* A bank has an id and list of customers.
*/
public class Bank {
    private long id;
    private List<Customer> customers;

    /**
     * Constructor for creating a Bank object.
     */
    public Bank(long bank_id) {
        this.id = bank_id;
        this.customers = new ArrayList<Customer>();
    }

    /**
     * Adds a customer to the list of customers.
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Outputs a summary of all the customers of the bank.
     * @return the customers summary as a string
     */
    public String customerSummary() {
        // Use StringBuilder instead of concatenating strings so as to avoid all the unnecessary copying
        StringBuilder summaryBuilder = new StringBuilder();
        summaryBuilder.append("Customer Summary");
        
        for (Customer c : customers)
            summaryBuilder.append("\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")");

        return summaryBuilder.toString();
    }

    /**
     * Makes sure the correct form(plural or singular) of the word is returned.
     * The form depends on the number argument passed.
     * @param number the number that determines the form of the word
     * @param word the word, the form of which will be determined
     * @return the number with the word in the correct form
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Calculates the total interest paid by the bank.
     * @return the total amount of paid interest
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * Returns the name of the first customer in the bank.
     * @return the name of the customer 
     */
    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        }
        catch (Exception e) {
            return "Error! Customer's name could not be retrieved. Perhaps there are no customers in the bank?";
        }
    }
}
