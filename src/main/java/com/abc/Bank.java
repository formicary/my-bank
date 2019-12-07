package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pete
 */
public class Bank {

    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
        
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        String s = "";
        for (Customer customer : customers) {
            s = s + "\nCustomer: " + customer.customerName + "\nAccounts: " + customer.getNumberOfAccounts();
        }
        
        return(s);
    }

    public double interestSummary() {
        String s = "";
        double totalInterest = 0;
        for (Customer customer : customers) {

            totalInterest += customer.getInterest();

        }
        return(totalInterest);
    }

}
