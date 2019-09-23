package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
    * Adds a new customer to the list of customers.
    */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
    * Produces an overview of all the customers that have any account in the bank.
    *
    * @return A formatted list of the customers and their number of accounts.
    */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + formatPlural(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
    * Makes sure the plural of the word passed is correct based on the number.
    * If number passed in is 1 just return the word, otherwise add an 's' at the end.
    *
    * @return The word in the correct plural form.
    */
    private String formatPlural(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
    * Calculates the total interest that has been paid to the customers.
    *
    * @return The sum of the interests paid to each customer.
    */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
    * Gets the name of the first customer.
    *
    * @return A String with the name of the first customer if they exist, or "No customers" if there are none.
    */
    public String getFirstCustomer() {
        if (customers.isEmpty())
            return "No customers.";
        else
            return customers.get(0).getName();
    }
}
