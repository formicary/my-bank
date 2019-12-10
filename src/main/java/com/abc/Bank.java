package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String getCustomerSummary() {
        String summary = "Customer Summary";

        if(customers.isEmpty()){
            summary += "\n- No customer accounts!";
        }
        // What if there's no customers?
        for (Customer c : customers)
        // Potentially hard to read, could give an example?
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    public String getInterestSummary(){
        String summary = "Interest Summary ";
        int numberOfAccounts = 0;
        double total = 0;

        for(Customer customer: customers){
            numberOfAccounts += customer.getNumberOfAccounts();
            total += customer.getTotalInterestEarned();
        }

        summary += "(" + format(numberOfAccounts, "account") + ")";
        summary += "\nInterest Paid: " +  toDollars(total);

        return summary;
    }

    // Do we need this?
    private String toDollars(double d){
        return String.format("$%,.2f", d);
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.getTotalInterestEarned();
        return total;
    }

// Is this method actually needed?
    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
