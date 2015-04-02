package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Bank represents the instance of a bank
 * and has customers
 * and methods to retrieve informations about customers
 * and interests paid to them
 */
public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * A bank manager can get a report showing the list of customers and how many accounts they have
     * @return Report for all the customers of the bank
     */
    public String customerSummary() {
        StringBuilder sb = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            sb.append("\n - ").append(c.getName()).append(" (").append(format(c.getNumberOfAccounts(), "account")).append(")");
        return String.valueOf(sb);
    }

    /**
     * Create the plural version of a word by adding an 's', based on the number passed in
     * If number passed in is 1 just return the word otherwise add an 's' at the end
     *
     * @param number number for the calculation
     * @param word   word that will be converted in plural
     * @return a string that show the number and the word
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * A bank manager can get a report showing the total interest paid by the bank on all accounts
     *
     * @return total interest paid for all the customers of the bank
     */
    public double getTotalInterestPaid() {
        double total = 0;
        for (Customer c : customers)
            total += c.getTotalInterestEarned();
        return total;
    }

    /**
     * Return the first customer of the bank
     *
     * @return the first customer or an Error message if bank has no customers
     */
    public String getFirstCustomer() {
        String result;
        if (!customers.isEmpty()) {
            result = customers.get(0).getName();
        } else {
            result = "Error, no customers found";
        }
        return result;
    }


}
