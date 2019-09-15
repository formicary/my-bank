package com.abc;

import com.abc.Customer.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a reference to all of the banks customers and provides summaries for the bank manager
 */
public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Add new customer to list of customers for bank
     * @param customer
     * @return
     */
    public Customer addCustomer (Customer customer) {
        customers.add(customer);
        return customer;
    }

    /**
     * @return Summary of all customers and the number of open accounts
     */
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

    /**
     * @return total interest paid to all customers
     */
    public Money totalInterestPaid() {
        Money total = new Money("0");
        for (Customer c : customers)
            total = total.add(c.totalInterestEarned());
        return total;
    }
}
