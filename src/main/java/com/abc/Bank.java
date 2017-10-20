package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    protected Bank() {
        this.customers = new ArrayList<Customer>();
    }

    protected void addCustomer(Customer customer) {
        this.customers.add(customer);
    }
    
//Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number) {
        String output;
        if (number==1){
            output = Integer.toString(number) +" account";
        } else {
            output = Integer.toString(number)+" accounts";
        }
        return output;
    }
    
    protected String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts()) + ")";
        return summary;
    }

    protected double totalInterestPaid() {
        double total;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    protected String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
