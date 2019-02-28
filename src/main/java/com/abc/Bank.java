package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }


    public void addCustomer(Customer customer) {
        if(customers.contains(customer)){
            throw new IllegalArgumentException("Customer is already a member of the bank");
        }else{
            customers.add(customer);
        }
    }

    public void removeCustomer(Customer customer){
        if(customers.contains(customer)){
            customers.remove(customer);
        }else{
            throw new IllegalArgumentException("Customer does not exist");
        }
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    public String getFirstCustomer() {
        if(customers.size() > 0){
            return customers.get(0).getName();
        }else{
            return "No Customers have joined the bank yet.";
        }
    }
}
