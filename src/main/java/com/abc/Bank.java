package com.abc;

import java.util.ArrayList;
import java.util.List;

/* -- Bank Class --
    An object that represents a Bank.
        A Bank has the following methods:
            - Bank() - Instantiates a bank. Initialises customer list to empty.
            - addCustomer(Customer customer) - Adds a provided customer to the
            member arraylist. 
            - customerSummary() - Returns a formatted textual statement of the 
            member customer list. 
            - double totalInterestPaid() - Returns the total interest paid
            to all customers across all customer accounts.
            - String getFirstCustomer() - Returns the first customer in the 
            member customerslist; if none exists (list is empty), throws an 
            exception. 
*/
public class Bank {
    private List<Customer> customers;

    // Instantiates a bank. Initialises customer list to empty. 
    public Bank() {
        this.customers = new ArrayList<Customer>();
    }

    // Adds a provided customer to the member arraylist. 
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    // Returns a formatted textual statement of the member customer list. 
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer customer : customers)
            summary += "\n - " + customer.getName() + " (" + 
                        format(customer.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    // Returns the total interest paid to all customers across all customer accounts.
    public double totalInterestPaidByAllCustomers() {
        double total = 0;
        for(Customer customer: customers)
            total += customer.totalInterestEarned();
        return total;
    }

    // Returns the first customer in the member customerslist; if none exists
    // (list is empty), returns some message to indicate as such. 
    public String getFirstCustomer() {
        if(this.customers.size() > 0) {
            return this.customers.get(0).getName();
        } else {
            return "There is no first customer; there are no customers";
        }
    }
}
