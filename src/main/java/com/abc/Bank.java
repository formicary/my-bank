package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;
    private Long IDcounter;

    /** Bank object constructor
     *
     */
    public Bank() {
        customers = new ArrayList<Customer>();
        IDcounter = 0L;
    }

    /** Adds an object of class Customer to Bank's list of customers.
     *  Throws exception if the customer already exists in the System.
     *
     * @param customer Customer object being added to Bank's list of customers
     *
     */
    public void addCustomer(Customer customer) {
        for (Customer c : customers) {
            if (c.getIdentifier().equals(customer.getIdentifier()) ){
                throw new IllegalArgumentException("Customer: " + customer.getIdentifier()+ " already exists!");
            }
        }
        customers.add(customer);
    }

    /** Removes customer from the bank system
     *
     * @param customer customer to be removed
     */
    public void removeCustomer(Customer customer){
        customers.remove(customer);
    }

    /** Generates ID
     *
     * @return Generated ID
     *
     */
    public String generateID(){
        IDcounter = IDcounter + 1L;
        return String.format("%06X", IDcounter);
    }

    /** Returns the total number of customers in the system.
     *
     * @return number of customers
     *
     */
    public int getNumberOfCustomers(){
        return customers.size();
    }

    /** Gives information of all customers in
     *
     * @return summary of all Customers (and their accounts) in String
     *
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() +" [" + c.getIdentifier() + "]"+
                    " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /** Makes sure correct plural of word is created based on the number passed in:
     *  If number passed in is 1 just return the word otherwise add an 's' at the end
     *
     * @param number the number being checked
     * @param word a word being transformed
     * @return formatted String
     *
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }


    /** Calculates how much Interest money has been paid to customers in total.
     *
     * @return total amount of interest paid
     *
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }
}
