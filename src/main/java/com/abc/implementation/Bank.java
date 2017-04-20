package com.abc.implementation;

import java.util.ArrayList;
import java.util.List;

import com.abc.interfaces.ICustomer;

public class Bank {
    private List<ICustomer> customers;

    public Bank() {
        customers = new ArrayList<ICustomer>();
    }

    public void addCustomer(ICustomer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (ICustomer c : customers)
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
        for(ICustomer c: customers)
            total += c.totalInterestEarned();
        return total;
    }
    
}
