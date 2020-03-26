package com.accenture.mybank;

import java.util.ArrayList;
import java.util.List;


public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * @param customer
     * This method adds customer
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * This method return customer summary with customer name and number of accounts
     * @return customerSummary
     */
    public String getCustomerSummary() {
    	StringBuilder customerSummary = new StringBuilder();
        customerSummary.append("Customer Summary");

        for (Customer customer : customers){
            customerSummary.append("\n").append(" - ").append(customer.getName()).append(" (");
            customerSummary.append(format(customer.getNumberOfAccounts(), "account") ).append(")");
        }
        return customerSummary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * This methods calculates total interest paid by bank till date
     * @return total
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer customer: customers)
            total += customer.totalInterestEarned();
        return total;
    }

    /**
     * This method gets the name of first customer in bank
     * @return first customer name
     * @throws Exception
     */
    public String getFirstCustomer() throws Exception {
    	return customers!=null && customers.size()!=0?customers.get(0).getName():"";
    }
}
