package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * This class allows the creation of a bank class to maintain a list of customers, produce a summary of each customer and produce an account of all interest paid to all customers 
 * @author tomhendra
 * @param customers	an arrayList of customers attributed to this bank object
 */
public class Bank {
    
    private List<Customer> customers;

    protected Bank() {
        this.customers = new ArrayList<Customer>();
    }
    
    /**
     * Scales the BigDecimal value to 2 decimal places and rounds to 'banking rounding' for calculations when combining big decimal values for readability
     * @param b BigDecimal to be scaled and rounded
     * @return the BigDecimal input as a new BigDecimal scaled to 2 decimal places and rounded using the 'bankers rounding' mode
     */
    protected static final BigDecimal scale(BigDecimal b) {
    		b=b.setScale(2, RoundingMode.HALF_EVEN);
    		return b;
    }

    /**
     * Adds customer objects to this banks customers arrayList
     * @param customer	customer input object to be added to this arrayList
     */
    protected void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    /**
     * Returns the correct plural or singular term of account depending on the number input
     * @param number	number of accounts attributed to the bank
     * @return	the correct plural or singular of account
     */
    private String format(int number) {
        String output;
        if (number==1) {
            output = String.format("%1$d %2$s",number, "account");
        } else {
            output = String.format("%1$d %2$s", number, "accounts");
        }
        return output;
    }
    
    /**
     * Produces a summary of the name and number of accounts held by each customer
     * @return summary of all customers in a readable form
     */
    protected String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers) {
            summary += String.format("%n - %1$s (%2$s)", c.getName(), format(c.getNumberOfAccounts()));
        }
        return summary;
    }

    /**
     * calculates and returns the total interest paid by the bank object to each attributed customer
     * @param convertedTotal	total value of interest paid by bank
     * @return total interest paid by bank as a BigDecimal
     */
    protected BigDecimal totalInterestPaid() {
        BigDecimal roundedTotal;
        roundedTotal = BigDecimal.ZERO;
        roundedTotal = scale(roundedTotal);
        for(Customer c: customers) {
            roundedTotal = roundedTotal.add(c.totalInterestEarned());
        }
        return roundedTotal;
    }

    /**
     * Returns the first indexed customer in the arrayList, if there are no customers added, a message stating the problem is returned
     * @return
     */
    protected String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "No customer found";
        }
    }
}
