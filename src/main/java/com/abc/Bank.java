package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + Utilities.format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }


    //TODO: make this as a report(?)
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * 
     * @return Customer's name
     */
    public String getFirstCustomerName() {
        if(customers.size() == 0)
            throw new IndexOutOfBoundsException("No customers in the bank");
        else
            return customers.get(0).getName();
    }
}
