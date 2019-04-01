package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.Data;

/**
 *  Class to represent a bank with all its data and functions.
 */
@Data
public class Bank {
    private List<Customer> customers = new ArrayList<Customer>();

    /**
     * Adds a customer to the bank.
     * @param customer the customer to be added
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Builds a string to summarize info about the customers accounts
     * @return the info about the accounts
     */
    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            summary.append("\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")");
        return summary.toString();
    }

    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Returns the total interest earned by the customers of the bank.
     * @return the sum of all the interest earned by the customers
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * Returns the first customer of the bank if it exists.
     * @return the first customer
     * @throws NoSuchElementException to indicate there are no customers for the bank
     */
    public Customer getFirstCustomer() throws NoSuchElementException {
        if(customers.size()==0) {
            throw new NoSuchElementException();
        }
        return customers.get(0);
    }

}
