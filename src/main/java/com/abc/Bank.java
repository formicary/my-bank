package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** The class {@code Bank} represents a bank. */
public class Bank {

    private final List<Customer> customers = new ArrayList<>();

    /** Initialise a Bank. */
    public Bank() {}

    /**
     * Add a customer to the bank.
     *
     * @param customer The customer
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Get the summary of all the customers related to the bank.
     *
     * @return The customers summary
     */
    public String getCustomersSummary() {
        return "Customer Summary"
                + customers
                .stream()
                .map(customer -> "\n - " + customer.getSummary())
                .collect(Collectors.joining());
    }

    /**
     * Get the total interest paid by customers that are related to the bank.
     *
     * @return The total interest paid
     */
    public BigDecimal getTotalInterestPaid() {
        return customers
                .stream()
                .map(Customer::getTotalInterestEarned)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Get the name of the first customer. Returns "No Customers" if there are no customers related to
     * the bank.
     *
     * @return The name of the first customer or "No Customers"
     */
    public String getFirstCustomerName() {
        if (customers.isEmpty()) {
            return "No Customers";
        }
        return customers.get(0).getName();
    }
}