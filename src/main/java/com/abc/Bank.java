package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Type representing the client bank. */
public class Bank {
    /** The bank\'s customers. */
    private List<Customer> customers;

    /** Instantiates a new Bank. */
    public Bank() {
        customers = new ArrayList<>();
    }

    /**
     * Add customer to bank\'s list of customers if the customer is not already present in the list.
     *
     * @param customer the customer to add
     * @return the added customers\'s id
     */
    public int addCustomer(Customer customer) {
        if (!customers.contains(customer)) customers.add(customer);
        return customer.getId();
    }

    /**
     * Returns the ID, surname, first name, and number of accounts for each of the bank\'s
     * customers. Sorted by ID.
     *
     * @return the summary formatted as a String table
     */
    public String customerSummary() {
        final String ID_FORMAT = "%-5s";
        final String NAME_FORMAT = "%-20s";
        final String ACCOUNTS_FORMAT = "%-9s";
        StringBuilder summery = new StringBuilder("Customer Summary:\n");
        summery.append(String.format(ID_FORMAT, "ID"))
                .append(String.format(NAME_FORMAT, "Surname"))
                .append(String.format(NAME_FORMAT, "First Name"))
                .append(String.format(ACCOUNTS_FORMAT, "Accounts\n"));

        // Sort customers by ID
        customers.sort(Comparator.comparing(Customer::getId));

        for (Customer customer : customers)
            summery.append(String.format(ID_FORMAT, customer.getId()))
                    .append(String.format(NAME_FORMAT, customer.getSurname()))
                    .append(String.format(NAME_FORMAT, customer.getFirstName()))
                    .append(String.format(ACCOUNTS_FORMAT, customer.getNumberOfAccounts()))
                    .append("\n");

        return summery.toString();
    }

    /**
     * Returns the parameter number followed by the parameter word. The word is pluralised if the
     * number does not equal 1.
     *
     * @param number the number
     * @param word the word
     * @return the formatted number and word
     */
    // Make sure correct plural of word is created based on the number passed in:
    // If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Calculates the combined interest earned on all of the bank\'s accounts
     *
     * @return the big decimal total interest paid
     */
    public BigDecimal totalInterestPaid() {
        BigDecimal total = BigDecimal.valueOf(0);
        for (Customer customer : customers) total = total.add(customer.totalInterestEarned());
        return total;
    }

    /**
     * Gets the first customer in the bank\'s list of customers
     *
     * @return the first customer
     */
    public Customer getFirstCustomer() {
        Customer customer = null;
        try {
            customer = customers.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    /**
     * Gets the customer with the parameter ID if such a customer is present in the bank\'s list of
     * customers.
     *
     * @param customerId the customer id
     * @return the customer with the parameter ID. Will return null if no such customer is found
     */
    public Customer getCustomer(int customerId) {
        Customer returnCustomer = null;
        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                returnCustomer = customer;
                break;
            }
        }
        return returnCustomer;
    }

    public String toString() {
        return customerSummary();
    }
}
