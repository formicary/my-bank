package com.abc;

import java.util.ArrayList;
import java.util.List;
/*-----------------------------------------------------------------------------
                                Bank Class
-----------------------------------------------------------------------------*/
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
            summary += "\n - " + c.getName() + " ("+c.getNumberOfAccounts()+" "+(c.getNumberOfAccounts()==1 ? "account" : "accounts")+")";

        return summary;
    }

    public double totalInterestPaid() {
        double total = 0.0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    public String getFirstCustomer() {
        if(customers.size()>=1){
            return customers.get(0).getName();
        }
        return "Error! Bank has no customers";
    }
}
