package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    /**
     * Bank Object Constructor
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * @param customer Customer to be added to the bank
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * @return Summary of customer names and the number of accounts in their name
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
     * @param number Number of aspects related to the word
     * @param word Word to be pluralised if required
     * @return The number given and the appropriate format of the word
     */
    // Formats plural of word to add an s on the end if required
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * @return Total interest paid by the bank for the current year
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * @return The name of the first customer in the customers list
     */
    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (IndexOutOfBoundsException e){
            return "No Customers Found";
        }
    }

    /**
     * Updates each customer's interest daily
     */
    public void dailyInterest() {
        for (Customer c : customers) {
            c.dailyInterest();
        }
    }
}
