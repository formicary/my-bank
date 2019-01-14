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
        //StringBuilder is more efficient in a loop.
        //If it was not in a loop the '+' operator would be just as good as it is converted to a StringBuilder anyway.
        StringBuilder summary = new StringBuilder();
        summary.append("Customer Summary");
        for (Customer c : customers)
        {
            summary.append("\n - ");
            summary.append(c.getName());
            summary.append(" (");
            summary.append(format(c.getNumberOfAccounts(), "account"));
            summary.append(")");
        }
        return summary.toString();
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

    //Removed as it was never called and no feature called for its existence.
    //public String getFirstCustomer() {
    //    try {
    //        customers = null;
    //        return customers.get(0).getName();
    //    } catch (Exception e){
    //        e.printStackTrace();
    //        return "Error";
    //    }
    //}
}
