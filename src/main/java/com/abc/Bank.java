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
            summary += "\n - " + c.getName() + " (" + pluraliseIfNecessary(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String pluraliseIfNecessary(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }
    
    public double totalBalanceInAllAccounts()
    {
        double total = 0;
        for(Customer c: customers)
            total += c.totalBalance();
        return total;
    }

    //Returns null if no customers
    public String getFirstCustomer() {
        if (customers.size()>0) return customers.get(0).getName();
        else return null;
    }
    
    public List<Integer> getCustomerIDs()
    {
        List<Integer> customerIDs = new ArrayList<>();
        for (Customer c : customers) {
            customerIDs.add(c.getID());
        }
        return customerIDs;
    }
    
    //returns null if no customer found with that ID;
    public Customer getCustomerByID(int customerID){
         for (Customer c : customers) {
            if (customerID==c.getID()) return c;
        }
        return null;
    }
}
