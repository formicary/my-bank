package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    //Constructor
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    //Add a customer to the Bank's ArrayList of customers
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    //Returns a list of formatted customer names
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

    //Calculate total interest paid to all customers
    public double totalInterestPaid(double years) {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned(years);
        return total;
    }

    //Returns the first customer from the ArrayList of customer's
    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
