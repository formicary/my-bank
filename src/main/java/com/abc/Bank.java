package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank(){
        this.customers = new ArrayList<Customer>();
    }

    // Adds a customer and ensures the customers list contains unique elements
    public void addCustomer(Customer customer){
        if(customers.contains(customer)){
            throw new IllegalArgumentException("Customer already exists.");
        } else {
            customers.add(customer);
        }
    }

    // Opens an account for a customer of this bank. Customers can have multiple accounts of the same type.
    public void openAccount(Customer customer, Account account){
        if(!customers.contains(customer)){
            throw new IllegalArgumentException("Customer must be a customer of this bank.");
        } else{
            customer.getAccounts().add(account);
            customer.incrementNumberOfAccounts();
        }
    }

    // Returns a report showing the number of accounts for each customer.
    public String customerSummary(){
        String summary = "Customer Summary";
        for(Customer c : customers){
            summary += "\n" + c.getName() + " (" +
                       format(c.getNumberOfAccounts()) + ")";
        }
        return summary;
    }

    // Helper function for customerSummary
    private String format(int number) {
        return number + " " + (number == 1 ? "account" : "accounts");
    }

    // Returns the total interest paid by the Bank to all of it's customers.
    public double totalInterestPaid(){
        double total = 0.0;
        for(Customer c: customers){
            for(Account a: c.getAccounts()){
                total += a.getInterestPaid();
            }
        }
        return total;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
