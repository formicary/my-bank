package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    // Return the lists of customers
    public List<Customer> getCustomers() {
        return this.customers;
    }

    // Add a new customer
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // Return a summary with the names of the customers and their number of accounts
    public String getCustomerSummary() {
        StringBuilder summary = new StringBuilder().append("Customer Summary");
        for (Customer customer : customers)
            summary.append("\n - " + customer.getName() + " (" + format(customer.getNumberOfAccounts(), "account") + ")");
        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double getTotalInterestPaid() {
        double total = 0;
        for(Customer customer: customers)
            total += customer.getTotalInterestEarned();
        return total;
    }

//    public String getFirstCustomer() {
//        try {
//            customers = null;
//            return customers.get(0).getName();
//        } catch (Exception e){
//            e.printStackTrace();
//            return "Error";
//        }
//    }
}
