package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank with a list of customers.
 */
public class Bank {

    /**
     * The list of customers associated with the bank.
     */
    private List<Customer> customers;

    /**
     * Constructs a new Bank instance with an empty list of customers.
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Adds a customer to the bank's list of customers.
     *
     * @param customer The customer to add.
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Generates a summary of the bank's customers and their accounts.
     *
     * @return A summary of customers and their accounts.
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
     * Formats a number and word into a grammatically correct phrase.
     *
     * @param number The number to format.
     * @param word   The word to format.
     * @return A formatted string.
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Calculates the total interest paid by all customers.
     *
     * @return The total interest paid.
     */
    public double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * Retrieves the name of the first customer in the bank's customer list.
     *
     * @return The name of the first customer or "No customers found." if the list
     *         is empty.
     */
    public String getFirstCustomer() {
        if (customers.isEmpty()) {
            return "No customers found.";
        } else {
            return customers.get(0).getName();
        }
    }  

    /**
     * Gets the number of customers in the bank.
     *
     * @return The number of customers.
     */
    public int getNumberOfCustomers() {
        return customers.size();
    }
}
