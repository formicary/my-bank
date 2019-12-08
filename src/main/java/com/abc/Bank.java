package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles bank-level functions such as adding customers and getting their summaries
 */
class Bank {

    /**
     * List of customers of the bank
     */
    private List<Customer> customers;

    /**
     * Constructor of the class
     */
    Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Function to add customers to the bank
     * @param customer customer to add to the bank
     */
    void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Function to get the summary of all the customers
     * @return the summary of all the customers
     */
    String customerSummary() {
        StringBuilder stringBuilder = new StringBuilder("Customer Summary");
        for (Customer c : customers) {
            stringBuilder.append("\n - ");
            stringBuilder.append(c.getName());
            stringBuilder.append(" (");
            stringBuilder.append(format(c.getNumberOfAccounts(), "account"));
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    /**
     * Function to make sure correct plural of word is created based on the number passed in. If number passed in is 1
     * just return the word otherwise add an 's' at the end
     * @param number number of items
     * @param word name of item
     * @return formatted string of item
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Function to calculate the total interest paid for all the customers
     * @return total interest paid to all customers of the bank
     */
    double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers) {
            total += c.totalInterestEarned();
        }
        return total;
    }

}
