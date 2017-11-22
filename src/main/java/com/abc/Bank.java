package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    /**
     * Bank constructor
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * AddCustomer method adds customer to the list of customers in the bank.
     * @param customer The customer to be added.
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * CustomerSummary displays the customer's name as well as the number of accounts the customer has.
     * @return Returns the customer summary.
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
     * Format method formats the word in its correct form.
     * @param number The number to be formatted.
     * @param word The word to be formatted.
     * @return Returns the correct format of the word.
     */
    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * TotalInterestPaid method calculates the total interest earned for all customers.
     * @return Returns the total interest earned for all customers.
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * GetFirstCustomer method gets the first customer.
     * @return Returns the first customer.
     */
    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
