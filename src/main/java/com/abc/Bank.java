package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Represents a bank.
 * @author James Rogers
 * @version 1.0
 * @since 1.0
*/
public class Bank {
    
    /** Customers in bank account. */
    private final List<Customer> customers;

    /** 
    * Creates a bank.
    */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
    * Adds a customer to the bank.
    * @param customer The customer to be added.
    */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
    * Gets the summary from all of the customers.
    * @return The formatted string of the customers summaries.
    */
    public String customerSummary() {
        
        StringBuilder summary = new StringBuilder();
        summary.append("Customer Summary");
        for (Customer c : customers) {
            summary.append(c.getSummary());
        }
        return summary.toString();
    }

    /**
    * Gets the total interest paid to all of the customers.
    * @return The total interest amount.
    */
    public BigDecimal totalInterestPaid() {
        BigDecimal total = BigDecimal.ZERO;
        for(Customer c: customers)
            total = total.add(c.totalInterestEarned());
        return total;
    }
}
