package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    /**
     * Adds new customer to the ArrayList storing all bank customers
     * @param customer object to be added to the ArrayList of bank customers
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Generates a summary of customer names and the number of accounts opened under their name
     * @return a string of all customers and the bank accounts
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer customer : customers)
            summary += "\n - " + customer.getName() + " (" + format(customer.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
     * Adds the correct suffix for plural to a given word depending on the quantity involved
     * @param number the number of account(s) involved
     * @param word a countable noun that needs a plural suffix, example: account to be turned into accounts
     * @return a string that has the correct plural ending depending on the number
     */
    private String format(int number, String word) {
        String outputString;

        if (number == 1) {
            outputString = String.format("%1$d %2$s", number, word);
        } else {
            outputString = String.format("%1$d %2$s", number, word + "s");
        }

        return outputString;
    }

    /**
     * Calculates the total amount of interest that was paid to the customers of the bank
     * @return value of total amount of interest paid
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer customer: customers)
            total += customer.totalInterestEarned();
        return total;
    }
}
