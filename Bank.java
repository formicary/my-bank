package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List<Customer> customers;

    public Bank() {
        this.customers = new ArrayList<>();
    }

    // creates new customer within bank
    // customer is created with unique id
    // return value is the new instance of Customer object
    public Customer createCustomer(String name) {
        Customer newCustomer = new Customer(name);
        customers.add(newCustomer);
        System.out.println("Customer with name " + newCustomer.getName() + " and ID " + newCustomer.getId() + " has been successfully created.");
        return newCustomer;
    }

    // prints the formatted output of all bank's customers and the number of accounts for each customer to console
    public void showCustomersOverview() {
        if (!customers.isEmpty()) {
            StringBuilder result = new StringBuilder();
            for(Customer customer:customers) {
                result.append("\n=======================================\n");
                result.append("Customer ID: "+customer.getId());
                result.append("\n");
                result.append("Customer's name: "+customer.getName());
                result.append("\n");
                result.append("Number of accounts: "+customer.getNumberOfAccounts());
            }
            System.out.println(result.toString());
        }else {
            System.out.println("No customers created so far.");
        }

    }

    // gets the list of all bank's customers and the number of accounts for each customer
    // return value is a List of CustomersOverview class instances containing long: customer ID, String: customer name, integer: number of accounts for each customer
    // if no customer exists in the bank, null is returned
    public List<CustomersOverview> getCustomersOverview() {
        if (!customers.isEmpty()) {
            List<CustomersOverview> customersOverview = new ArrayList<>();
            for (Customer customer : customers) {
                customersOverview.add(new CustomersOverview(customer.getId(), customer.getName(),customer.getNumberOfAccounts()));
            }
            return customersOverview;
        } else {
            return null;
        }
    }


    // prints the formatted output of cumulative total interest paid on all accounts of the bank
    public void showTotalInterestPaid() {
        double interestPaid = 0d;
        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                for (Account account : customer.getAccounts()) {
                    interestPaid += account.interestPaidOnAccount();
                }
            }
        }
        System.out.println("Total interest paid by the bank to all customers: "+interestPaid);
    }

    // gets the cumulative total interest paid on all accounts of the bank
    // returns a double value of all interest paid
    public double getTotalInterestPaid() {
        double interestPaid = 0d;
        for(Customer customer : customers) {
            for (Account account : customer.getAccounts()) {
                interestPaid += account.interestPaidOnAccount();
            }
        }
        return interestPaid;
    }

    // creates a copy of List, so no customers can be added or deleted directly
    // returns a List of customers referencing the original Customer instances
    /*public List<Customer> getCustomers() {
        List<Customer> customersCopy = new ArrayList<>(customers);
        return customersCopy;
    }*/

    // only temporary use for testing purposes
    public List<Customer> getCustomers() {
        return customers;
    }
}