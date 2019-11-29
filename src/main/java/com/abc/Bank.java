package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bank {
    // Store customers
    private List<Customer> bankCustomers;

    /**
     * Constructor for bank
     */
    public Bank() {
        bankCustomers = new ArrayList<Customer>();
    }
    /**
     * Add one or multiple customers to the list of customers in the bank
     * @param customers
     */
    public void addCustomer(Customer... customers) {
        bankCustomers.addAll(Arrays.asList(customers));
    }
    /**
     * Get a summary of all the customers in the bank, including their IDs, names and
     * number of opened accounts
     * @return
     */
    public StringBuilder customerSummary() {
        StringBuilder customerSummary = new StringBuilder();
        customerSummary.append("Customer Summary:\n");
        customerSummary.append(String.format("%-20s%-20s%-20s%-20s\n", "Customer ID",
                "First Name",
                "Last Name",
                "No. of Accounts"));
        for(Customer customer : bankCustomers) {
            customerSummary.append(String.format("%-20s%-20s%-20s%-20s\n",
                    customer.getCustomerId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getNumberOfAccounts()));
        }
        return customerSummary;
    }
    /**
     * Get a report showing the amount of interest the bank has to pay in total
     * @return
     */
    public StringBuilder totalInterestReport() {
        StringBuilder report = new StringBuilder();
        report.append("Total interest paid by the Bank: " + toDollars(totalInterestPaid()));
        return report;
    }

    // Calculate the total interest paid by the bank
    private BigDecimal totalInterestPaid() {
        BigDecimal totalInterest = BigDecimal.valueOf(0);
        for (Customer customers : bankCustomers)
            totalInterest = totalInterest.add(customers.totalInterestEarned());
        return totalInterest;
    }

    // Convert the amount of money to dollar format
    private String toDollars(BigDecimal amount){
        return String.format("$%,.2f", amount.abs());
    }

    // Getters
    /**
     * Get the list of customers belonging to the bank
     * @return
     */
    public List<Customer> getBankCustomers() {
        return bankCustomers;
    }
}
