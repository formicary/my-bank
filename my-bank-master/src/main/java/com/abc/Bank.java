package com.abc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bank {
    private LinkedList<Customer> customers;

    /*
        Assuming this is a popular bank, the number of customers joining and leaving the bank will have a high rate.
        Because of this, I choose a linked list as an ArrayList has an O(n) deletion time. If we have to shift every
        customer over one in the array list all the time this will have very poor performance. Beyond that, we identify
        customers by searching which, in an array list, is O(n) just like a linked list. So linked list is better here.
        A set, queue and hash table are not appropriate here at all.
     */

    public Bank() {
        customers = new LinkedList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
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
}
