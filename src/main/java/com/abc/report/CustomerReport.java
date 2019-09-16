package com.abc.report;

import com.abc.customer.Customer;

import java.util.ArrayList;

public class CustomerReport {

    private ArrayList<Customer> customers;

    public CustomerReport(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    /**
     * previously customerSummary
     * @return Summary of all customers and the number of open accounts
     */
    public String getCustomerReport() {
        String summary = "customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
}
