package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    //TODO: can be final
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers) {
            summary.append(String.format("\n - %s (%s)", c.getName(), format(c.getNumberOfAccounts(), "account")));
        }
        return summary.toString();
    }

    //TODO: method could be better named to increase readability
    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        //TODO: could use a stream for more readable code
        for(Customer c: customers) {
            total += c.totalInterestEarned();
        }
        return total;
    }

    //TODO: this method is never used it can be removed
    public String getFirstCustomer() {
        //TODO: catching an exception here is not the best thing to do can just check the size of the customers List
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e){  //TODO: should never catch Exception always catch a specific exception when needed
            e.printStackTrace();
            return "Error";
        }
    }
}
