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
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public Money totalInterestPaid() {
        Money total = new Money("0.00");
        for(Customer c: customers)
        	//TODO - Not scalable - needs revision
            total = new Money(total.getAmount().add(c.totalInterestEarned().getAmount()));
        return total;
    }

    // Note: not 100% sure what this function is supposed to do because of the strange pre-existing code. Changed it to get first cust
    // if one exists
    public String getFirstCustomer() {
        if(customers.size() > 0)
        	return customers.get(0).getName();
        else{
        	throw new IndexOutOfBoundsException("No customers in list");
        }
    }
}
