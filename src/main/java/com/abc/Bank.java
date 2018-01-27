package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the object for the Bank.
 * It contains an ArrayList of the customers.
 * @author Matthew Howard - <a href="mailto:m.o.howard@outlook.com">m.o.howard@outlook.com</a>
 */

public class Bank {
    private List<Customer> customers;

    /**
     *This is the constructor it initialises the ArrayList of customers to be empty
    */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     *
     * @param customer
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }


    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers) {
            summary += "\n - " + c.getName() + " (" + pluraliseString(c.getNumberOfAccounts(), "account") + ")";
        }
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String pluraliseString(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers) {
            total += c.totalInterestEarned();
        }
        return total;
    }


}
