package com.abc.MainClasses;

import java.util.ArrayList;
import java.util.List;

import com.abc.Interfaces.BankInterface;

public class Bank implements BankInterface{
    private List<Customer> customers;
    
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        if (customers.size() == 0) {
            return "There isn't a customer registered with the bank.";
        } else {
            String summary = "Customer Summary", strOverallTotal = "";
            double overallTotal = 0;

            for (Customer c : customers) {
                overallTotal += Double.parseDouble(c.getStatement()[1]);
                strOverallTotal = c.toDollars(overallTotal);

                summary += "\n\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")"
                + " - " + c.getStatement()[0];
            }

            summary += "\n\nTotal Of All Recorded Accounts : " + strOverallTotal;

            return summary;
        }
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    public String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;

        for(Customer c: customers)
            total += c.totalInterestEarned();
            
        return Double.valueOf(String.format("%.2f", total));
    }

    //Retrieve first customer in customer list
    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }

    //Retrieve last customer in customer list
    public String getLastCustomer() {
        try {
            return customers.get(customers.size() - 1).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}